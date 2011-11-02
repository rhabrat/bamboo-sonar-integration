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

import com.atlassian.bamboo.build.Buildable;
import com.atlassian.bamboo.task.TaskConfigConstants;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.v2.build.agent.capability.Requirement;
import com.marvelution.bamboo.plugins.sonar.tasks.SonarMaven3Config;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * {@link AbstractSonarMavenBuildTaskConfigurator} implementation for the Sonar Maven 3 builder
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarMaven3BuildTaskConfigurator extends AbstractSonarMavenBuildTaskConfigurator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NotNull
	public Set<Requirement> calculateRequirements(TaskDefinition taskDefinition, Buildable buildable) {
		final Set<Requirement> requirements = super.calculateRequirements(taskDefinition, buildable);
		taskConfiguratorHelper.addSystemRequirementFromConfiguration(requirements, taskDefinition,
			TaskConfigConstants.CFG_BUILDER_LABEL, SonarMaven3Config.M3_CAPABILITY_PREFIX);
		return requirements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSonarMavenPluginVersion() {
		return SONAR_M3_PLUGIN_VERSION;
	}

}
