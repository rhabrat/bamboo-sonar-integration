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

package com.marvelution.bamboo.plugins.sonar.tasks.actions.admin;

import org.apache.log4j.Logger;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.plan.PlanKeys;
import com.atlassian.bamboo.task.ImmutableTaskDefinition;
import com.atlassian.bamboo.task.TaskConfigurationService;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.task.TaskManager;
import com.atlassian.bamboo.task.TaskModuleDescriptor;
import com.atlassian.bamboo.ww2.actions.build.admin.config.task.ConfigureBuildTasks;
import com.google.common.collect.Iterables;
import com.marvelution.bamboo.plugins.sonar.tasks.predicates.SonarPredicates;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarTaskUtils;

/**
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class UpdateSonarBuildTaskConfiguration extends ConfigureBuildTasks {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UpdateSonarBuildTaskConfiguration.class);

	private TaskManager taskManager;
	private TaskConfigurationService taskConfigurationService;

	/**
	 * Action method to update the Sonar Task Configuration using the {@link TaskModuleDescriptor}
	 * 
	 * @return the resulting view name
	 * @throws Exception in case of errors
	 */
	public String doPerformUpdate() throws Exception {
		for (Job job : SonarTaskUtils.getJobsWithSonarTasks(getPlan())) {
			for (TaskDefinition taskDefinition : Iterables.filter(job.getBuildDefinition().getTaskDefinitions(),
				SonarPredicates.isSonarTask())) {
				TaskDefinition currectTaskDefinition = new ImmutableTaskDefinition(taskDefinition);
				TaskModuleDescriptor descriptor = taskManager.getTaskDescriptor(currectTaskDefinition.getPluginKey());
				try {
					TaskDefinition newTaskDefinition =
						taskConfigurationService.editTask(PlanKeys.getPlanKey(getPlanKey()),
							currectTaskDefinition.getId(), currectTaskDefinition.getUserDescription(),
							getTaskConfigurationMap(descriptor, currectTaskDefinition));
					LOGGER.info("Successfully update task " + newTaskDefinition.getId() + " of plan: " + getPlanKey());
				} catch (Exception e) {
					String message = getText("bulkAction.sonar.task.update.failure",
						String.valueOf(currectTaskDefinition.getId()), currectTaskDefinition.getUserDescription());
					addActionError(message);
					LOGGER.error(getPlanKey() + ": " + message, e);
				}
			}
		}
		if (getTotalErrors() > 0) {
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Setter for taskManager
	 * 
	 * @param taskManager the taskManager to set
	 */
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	/**
	 * Setter for taskConfigurationService
	 * 
	 * @param taskConfigurationService the taskConfigurationService to set
	 */
	public void setTaskConfigurationService(TaskConfigurationService taskConfigurationService) {
		this.taskConfigurationService = taskConfigurationService;
	}

}
