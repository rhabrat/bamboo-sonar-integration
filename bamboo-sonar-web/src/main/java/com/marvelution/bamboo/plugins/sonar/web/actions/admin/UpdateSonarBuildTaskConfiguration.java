/*
 * Licensed to Marvelution under one or more contributor license 
 * agreements.  See the NOTICE file distributed with this work 
 * for additional information regarding copyright ownership.
 * Marvelution licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.marvelution.bamboo.plugins.sonar.web.actions.admin;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.plan.PlanKeys;
import com.atlassian.bamboo.task.ImmutableTaskDefinition;
import com.atlassian.bamboo.task.TaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.task.TaskModuleDescriptor;
import com.atlassian.bamboo.webwork.util.ActionParametersMapImpl;
import com.atlassian.bamboo.ww2.actions.admin.bulk.BulkAction;
import com.atlassian.bamboo.ww2.actions.build.admin.config.task.ConfigureBuildTasks;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarTaskConfigurator;
import com.marvelution.bamboo.plugins.sonar.tasks.predicates.SonarPredicates;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarTaskUtils;
import com.marvelution.bamboo.plugins.sonar.web.actions.admin.bulk.SonarBulkAction;
import com.opensymphony.xwork.ActionContext;

/**
 * {@link ConfigureBuildTasks} action to update the Soanr tasks via the {@link SonarBulkAction}s
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class UpdateSonarBuildTaskConfiguration extends ConfigureBuildTasks {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UpdateSonarBuildTaskConfiguration.class);

	private String selectedBulkActionKey;
	private List<BulkAction> availableBulkActions;
	private SonarBulkAction sonarBulkAction;

	/**
	 * Action method to update the Sonar Task Configuration using the {@link TaskModuleDescriptor}
	 * 
	 * @return the resulting view name
	 * @throws Exception in case of errors
	 */
	public String doPerformUpdate() throws Exception {
		for (Job job : SonarTaskUtils.getJobsWithSonarTasks(getPlan())) {
			LOGGER.debug("Checking Job " + job.getKey());
			for (TaskDefinition taskDefinition : Iterables.filter(job.getBuildDefinition().getTaskDefinitions(),
				SonarPredicates.isSonarTask())) {
				LOGGER.debug("Checking TaskDefinition " + taskDefinition.getId() + " "
					+ taskDefinition.getPluginKey());
				TaskDefinition currectTaskDefinition = new ImmutableTaskDefinition(taskDefinition);
				TaskModuleDescriptor descriptor = taskManager.getTaskDescriptor(currectTaskDefinition.getPluginKey());
				if (isTaskConfigurationValid(descriptor)) {
					try {
						TaskDefinition newTaskDefinition =
							taskConfigurationService.editTask(PlanKeys.getPlanKey(job.getKey()),
								currectTaskDefinition.getId(), currectTaskDefinition.getUserDescription(),
								getTaskConfigurationMap(descriptor, currectTaskDefinition),
								currectTaskDefinition.getRootDirectorySelector());
						LOGGER.info("Successfully update task " + newTaskDefinition.getId() + " of plan: "
							+ job.getKey());
					} catch (Exception e) {
						String message = getText("bulkAction.sonar.task.update.failure",
							String.valueOf(currectTaskDefinition.getId()), currectTaskDefinition.getUserDescription());
						addActionError(message);
						LOGGER.error(job.getKey() + ": " + message, e);
					}
				} else {
					LOGGER.warn(job.getKey() + ": New Task Configuration was not valid, skipping update");
					addActionError(getText("bulkAction.sonar.task.config.invalid",
						String.valueOf(currectTaskDefinition.getId()), currectTaskDefinition.getUserDescription()));
				}
			}
		}
		if (getTotalErrors() > 0) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, String> getTaskConfigurationMap(TaskModuleDescriptor taskDescriptor,
					TaskDefinition previousTaskDefinition) {
		TaskConfigurator taskConfigurator = taskDescriptor.getTaskConfigurator();
		SonarBulkAction bulkAction = getSonarBulkAction();
		if (taskConfigurator != null && bulkAction != null) {
			// Get the Bulk Action related parameters from the ActionContext
			Map<String, String> parameters =
				bulkAction.getTaskConfigurationMap(ActionContext.getContext().getParameters());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Updating task configuration with:");
				for (Entry<String, String> entry : parameters.entrySet()) {
					LOGGER.debug("Parameter '" + entry.getKey() + "' with value: " + entry.getValue());
				}
			}
			// Copy previous task definition that was not in the bulk action but needs to be copied over
			for (Entry<String, String> entry : previousTaskDefinition.getConfiguration().entrySet()) {
				if (!parameters.containsKey(entry.getKey())) {
					// Found a configuration item that was not included in the Bulk Action copy it so it won't get lost
					LOGGER.debug("Copying parameter '" + entry.getKey() + "' with value: " + entry.getValue());
					parameters.put(entry.getKey(), entry.getValue());
				}
			}
			return taskConfigurator.generateTaskConfigMap(new ActionParametersMapImpl(parameters),
				previousTaskDefinition);
		}
		return Maps.newHashMap();
	}

	/**
	 * Internal method to check if the given task configuration is valid
	 * 
	 * @param descriptor the {@link TaskModuleDescriptor} of the Task to check
	 * @return <code>true</code> if valid, <code>false</code> otherwise
	 */
	private boolean isTaskConfigurationValid(TaskModuleDescriptor descriptor) {
		if (getSonarBulkAction() != null && descriptor.getTaskConfigurator() != null
				&& descriptor.getTaskConfigurator() instanceof SonarTaskConfigurator) {
				int errorCount = getTotalErrors();
			final SonarTaskConfigurator taskConfigurator = (SonarTaskConfigurator) descriptor.getTaskConfigurator();
			getSonarBulkAction().validateTaskConfiguration(taskConfigurator,
				new ActionParametersMapImpl(ActionContext.getContext()), this);
			if (getTotalErrors() > errorCount) {
				// New errors added, Configuration is not valid
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	/**
	 * Get the {@link SonarBulkAction} by the {@link #selectedBulkActionKey}
	 * 
	 * @return the {@link SonarBulkAction}, may be <code>null</code> if no action is found, this should not happen
	 */
	protected SonarBulkAction getSonarBulkAction() {
		if (sonarBulkAction == null) {
			for (BulkAction action : getAvailableBulkActions()) {
				if (action instanceof SonarBulkAction && action.getKey().equals(getSelectedBulkActionKey())) {
					LOGGER.debug("Located the SonarBulkAction " + action.getKey());
					sonarBulkAction = (SonarBulkAction) action;
					break;
				}
			}
			if (sonarBulkAction == null) {
				LOGGER.warn("Failed to locate the SonarBulkAction, the bulk action will be skipped");
			}
		}
		return sonarBulkAction;
	}

	/**
	 * Getter for selectedBulkActionKey
	 * 
	 * @return the selectedBulkActionKey
	 */
	public String getSelectedBulkActionKey() {
		return selectedBulkActionKey;
	}

	/**
	 * Setter for selectedBulkActionKey
	 * 
	 * @param selectedBulkActionKey the selectedBulkActionKey to set
	 */
	public void setSelectedBulkActionKey(String selectedBulkActionKey) {
		this.selectedBulkActionKey = selectedBulkActionKey;
	}

	/**
	 * Getter for availableBulkActions
	 * 
	 * @return the availableBulkActions
	 */
	public List<BulkAction> getAvailableBulkActions() {
		return availableBulkActions;
	}

	/**
	 * Setter for availableBulkActions
	 * 
	 * @param availableBulkActions the availableBulkActions to set
	 */
	public void setAvailableBulkActions(List<BulkAction> availableBulkActions) {
		this.availableBulkActions = availableBulkActions;
	}

}
