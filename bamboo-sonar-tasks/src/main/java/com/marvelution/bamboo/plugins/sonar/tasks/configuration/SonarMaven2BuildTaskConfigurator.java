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

import com.atlassian.bamboo.plugins.maven.task.Maven2Config;
import com.atlassian.bamboo.task.TaskConfigConstants;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.v2.build.agent.capability.Requirement;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * {@link AbstractSonarMavenBuildTaskConfigurator} implementation for the Sonar Maven 2 builder
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarMaven2BuildTaskConfigurator extends AbstractSonarMavenBuildTaskConfigurator {

	public static final String SONAR_PLUGIN_VERSION = "1.0-beta-2";

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NotNull
	public Set<Requirement> calculateRequirements(@NotNull TaskDefinition taskDefinition) {
		final Set<Requirement> requirements = super.calculateRequirements(taskDefinition);
		taskConfiguratorHelper.addSystemRequirementFromConfiguration(requirements, taskDefinition,
			TaskConfigConstants.CFG_BUILDER_LABEL, Maven2Config.M2_CAPABILITY_PREFIX);
		return requirements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSonarMavenPluginVersion() {
		return SONAR_PLUGIN_VERSION;
	}

}
