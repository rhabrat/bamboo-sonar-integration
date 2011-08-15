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
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskConfigConstants;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.task.TaskRequirementSupport;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.v2.build.agent.capability.Requirement;
import com.atlassian.bamboo.ww2.actions.build.admin.create.UIConfigBean;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.opensymphony.xwork.TextProvider;

/**
 * Base implementation of the {@link AbstractTaskConfigurator} for all the Sonar Tasks
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public abstract class AbstractSonarBuildTaskConfigurator extends AbstractTaskConfigurator implements
		TaskRequirementSupport, SonarConfigConstants {

	private static final List<String> FIELDS_TO_COPY = ImmutableList.of(CFG_SONAR_HOST_URL, CFG_SONAR_HOST_USERNAME,
		CFG_SONAR_HOST_PASSWORD, CFG_SONAR_JDBC_URL, CFG_SONAR_JDBC_USERNAME, CFG_SONAR_JDBC_PASSWORD,
		CFG_SONAR_JDBC_DRIVER, CFG_SONAR_LANGUAGE, CFG_SONAR_JAVA_SOURCE, CFG_SONAR_JAVA_TARGET);

	protected TextProvider textProvider;
	protected UIConfigBean uiConfigBean;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Requirement> calculateRequirements(TaskDefinition taskDefinition) {
		final Set<Requirement> requirements = Sets.newHashSet();
		taskConfiguratorHelper.addJdkRequirement(requirements, taskDefinition, TaskConfigConstants.CFG_JDK_LABEL);
		return requirements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection) {
		taskConfiguratorHelper.validateBuilderLabel(params, errorCollection);
		taskConfiguratorHelper.validateJdk(params, errorCollection);
		if (StringUtils.isBlank(params.getString(CFG_SONAR_HOST_URL))) {
			errorCollection.addError(CFG_SONAR_HOST_URL, textProvider.getText("sonar.host.url.mandatory"));
		} else if (!params.getString(CFG_SONAR_HOST_URL).startsWith("http://")
			&& !params.getString(CFG_SONAR_HOST_URL).startsWith("https://")) {
			errorCollection.addError(CFG_SONAR_HOST_URL, textProvider.getText("sonar.host.url.invalid"));
		}
	}

	/**
	 * Internal method to validate the Sonar Server configuration
	 * 
	 * @param params the {@link ActionParametersMap}
	 * @param errorCollection the {@link ErrorCollection}
	 */
	protected void validateSonarServer(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection) {
		// TODO Validate the JDBC settings if non default
	}

	/**
	 * {@inheritDoc}
	 */
	@NotNull
	@Override
	public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params,
					@Nullable final TaskDefinition previousTaskDefinition) {
		Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);
		taskConfiguratorHelper.populateTaskConfigMapWithActionParameters(config, params,
			Iterables.concat(TaskConfigConstants.DEFAULT_BUILDER_CONFIGURATION_KEYS, FIELDS_TO_COPY));
		return config;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateContextForCreate(@NotNull Map<String, Object> context) {
		super.populateContextForCreate(context);
		populateContextForAllOperations(context);
		context.put(TaskConfigConstants.CFG_JDK_LABEL, uiConfigBean.getDefaultJdkLabel());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateContextForEdit(@NotNull Map<String, Object> context, @NotNull TaskDefinition taskDefinition) {
		super.populateContextForEdit(context, taskDefinition);
		populateContextForAllOperations(context);
		taskConfiguratorHelper.populateContextWithConfiguration(context, taskDefinition,
			Iterables.concat(TaskConfigConstants.DEFAULT_BUILDER_CONFIGURATION_KEYS, FIELDS_TO_COPY));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateContextForView(@NotNull Map<String, Object> context, @NotNull TaskDefinition taskDefinition) {
		super.populateContextForView(context, taskDefinition);
		populateContextForAllOperations(context);
		taskConfiguratorHelper.populateContextWithConfiguration(context, taskDefinition,
			Iterables.concat(TaskConfigConstants.DEFAULT_BUILDER_CONFIGURATION_KEYS, FIELDS_TO_COPY));
		if (context.containsKey(CFG_SONAR_JDBC_PASSWORD)) {
			// Add a fake password context variable to display the password
			context.put(CFG_SONAR_JDBC_PASSWORD, SONAR_FAKE_PASSWORD);
		}
		if (context.containsKey(CFG_SONAR_HOST_PASSWORD)) {
			// Add a fake password context variable to display the password
			context.put(CFG_SONAR_HOST_PASSWORD, SONAR_FAKE_PASSWORD);
		}
		// TODO Override JDBC settings if using defaults
	}

	/**
	 * Setter for the {@link TextProvider}
	 * 
	 * @param textProvider the {@link TextProvider}
	 */
	public void setTextProvider(TextProvider textProvider) {
		this.textProvider = textProvider;
	}

	/**
	 * Setter for the {@link UIConfigBean}
	 * 
	 * @param uiConfigBean the {@link UIConfigBean}
	 */
	public void setUiConfigBean(UIConfigBean uiConfigBean) {
		this.uiConfigBean = uiConfigBean;
	}

	/**
	 * Setter for the context that is applicable for both view and edit
	 * 
	 * @param context the {@link Map} context to set
	 */
	protected void populateContextForAllOperations(@NotNull Map<String, Object> context) {
		context.put(CTX_UI_CONFIG_BEAN, uiConfigBean);
	}

}
