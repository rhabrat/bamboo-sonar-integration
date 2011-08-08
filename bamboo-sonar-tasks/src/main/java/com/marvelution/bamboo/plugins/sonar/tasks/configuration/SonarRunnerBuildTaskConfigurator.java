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

package com.marvelution.bamboo.plugins.sonar.tasks.configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.TaskConfigConstants;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.agent.capability.Requirement;
import com.google.common.collect.ImmutableList;
import com.marvelution.bamboo.plugins.sonar.tasks.SonarRunnerCapabilityDefaultsHelper;

/**
 * {@link AbstractSonarBuildTaskConfigurator} implementation for the Sonar Runner task
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarRunnerBuildTaskConfigurator extends AbstractSonarBuildTaskConfigurator implements
		SonarConfigConstants {

	private static final List<String> FIELDS_TO_COPY = ImmutableList.of(CFG_SONAR_PROJECT_KEY,
		CFG_SONAR_PROJECT_NAME, CFG_SONAR_PROJECT_VERSION, CFG_SONAR_SOURCES, CFG_SONAR_TESTS, CFG_SONAR_BINARIES,
		CFG_SONAR_LIBRARIES, CFG_SONAR_SERVER_CONFIGURED, CFG_SONAR_PROJECT_CONFIGURED);

	/**
	 * {@inheritDoc}
	 */
	@NotNull
	@Override
	public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params,
					@Nullable final TaskDefinition previousTaskDefinition) {
		Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
		taskConfiguratorHelper.populateTaskConfigMapWithActionParameters(config, params, FIELDS_TO_COPY);
		return config;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NotNull
	public Set<Requirement> calculateRequirements(@NotNull TaskDefinition taskDefinition) {
		final Set<Requirement> requirements = super.calculateRequirements(taskDefinition);
		taskConfiguratorHelper.addSystemRequirementFromConfiguration(requirements, taskDefinition,
			TaskConfigConstants.CFG_BUILDER_LABEL, SonarRunnerCapabilityDefaultsHelper.SONAR_RUNNER_PREFIX);
		return requirements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection) {
		super.validate(params, errorCollection);
		if (!params.getBoolean(CFG_SONAR_SERVER_CONFIGURED)) {
			validateSonarServer(params, errorCollection);
		}
		if (!params.getBoolean(CFG_SONAR_PROJECT_CONFIGURED)) {
			if (StringUtils.isBlank(params.getString(CFG_SONAR_PROJECT_KEY))) {
				errorCollection.addError(CFG_SONAR_PROJECT_KEY, textProvider.getText("sonar.project.key.mandatory"));
			} else if (params.getString(CFG_SONAR_PROJECT_KEY).indexOf(":") == -1) {
				errorCollection.addError(CFG_SONAR_PROJECT_KEY, textProvider.getText("sonar.project.key.invalid"));
			}
			if (StringUtils.isBlank(params.getString(CFG_SONAR_PROJECT_NAME))) {
				errorCollection.addError(CFG_SONAR_PROJECT_NAME, textProvider.getText("sonar.project.name.mandatory"));
			}
			if (StringUtils.isBlank(params.getString(CFG_SONAR_PROJECT_VERSION))) {
				errorCollection.addError(CFG_SONAR_PROJECT_VERSION,
					textProvider.getText("sonar.project.version.mandatory"));
			}
			if (StringUtils.isBlank(params.getString(CFG_SONAR_SOURCES))) {
				errorCollection.addError(CFG_SONAR_SOURCES, textProvider.getText("sonar.sources.mandatory"));
			} else if (StringUtils.split(params.getString(CFG_SONAR_SOURCES), DIRECTORY_SEPARATOR).length < 1) {
				errorCollection.addError(CFG_SONAR_SOURCES, textProvider.getText("sonar.sources.mandatory"));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateContextForCreate(Map<String, Object> context) {
		super.populateContextForCreate(context);
		context.put(CFG_SONAR_SERVER_CONFIGURED, String.valueOf(Boolean.FALSE));
		context.put(CFG_SONAR_PROJECT_CONFIGURED, String.valueOf(Boolean.FALSE));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateContextForEdit(@NotNull Map<String, Object> context, @NotNull TaskDefinition taskDefinition) {
		super.populateContextForEdit(context, taskDefinition);
		taskConfiguratorHelper.populateContextWithConfiguration(context, taskDefinition, FIELDS_TO_COPY);
		boolean serverConfig = Boolean.valueOf(taskDefinition.getConfiguration().get(CFG_SONAR_SERVER_CONFIGURED));
		boolean projectConfig = Boolean.valueOf(taskDefinition.getConfiguration().get(CFG_SONAR_PROJECT_CONFIGURED));
		if (!serverConfig) {
			context.put(CFG_SONAR_SERVER_CONFIGURED, String.valueOf(Boolean.FALSE));
		}
		if (!projectConfig) {
			context.put(CFG_SONAR_PROJECT_CONFIGURED, String.valueOf(Boolean.FALSE));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateContextForView(@NotNull Map<String, Object> context, @NotNull TaskDefinition taskDefinition) {
		super.populateContextForView(context, taskDefinition);
		taskConfiguratorHelper.populateContextWithConfiguration(context, taskDefinition, FIELDS_TO_COPY);
		context.put(CFG_SONAR_SERVER_CONFIGURED,
			Boolean.valueOf(taskDefinition.getConfiguration().get(CFG_SONAR_SERVER_CONFIGURED)));
		context.put(CFG_SONAR_PROJECT_CONFIGURED,
			Boolean.valueOf(taskDefinition.getConfiguration().get(CFG_SONAR_PROJECT_CONFIGURED)));
	}

}
