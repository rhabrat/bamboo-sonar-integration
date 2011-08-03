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

package com.marvelution.bamboo.plugins.sonar.tasks;

import com.atlassian.bamboo.plugins.maven.task.Maven3Config;
import com.atlassian.bamboo.process.EnvironmentVariableAccessor;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;

/**
 * Sonar Maven 3.x {@link TaskType} implementation
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarMaven3BuildTask extends AbstractSonarMavenBuildTask<Maven3Config> {

	/**
	 * Default Constructor
	 * 
	 * @param capabilityContext the {@link CapabilityContext} implementation
	 * @param environmentVariableAccessor the {@link EnvironmentVariableAccessor} implementation
	 * @param processService the {@link ProcessService} implementation
	 */
	public SonarMaven3BuildTask(CapabilityContext capabilityContext,
		EnvironmentVariableAccessor environmentVariableAccessor, ProcessService processService) {
		super(capabilityContext, environmentVariableAccessor, processService);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Maven3Config getMavenConfiguration(TaskContext taskContext) {
		return new Maven3Config(taskContext, capabilityContext, environmentVariableAccessor);
	}

}
