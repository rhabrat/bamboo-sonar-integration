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

import static com.atlassian.bamboo.task.TaskConfigConstants.CFG_BUILDER_LABEL;
import static com.atlassian.bamboo.task.TaskConfigConstants.CFG_ENVIRONMENT_VARIABLES;
import static com.marvelution.bamboo.plugins.sonar.tasks.SonarRunnerCapabilityDefaultsHelper.SONAR_RUNNER_EXECUTABLE;
import static com.marvelution.bamboo.plugins.sonar.tasks.SonarRunnerCapabilityDefaultsHelper.SONAR_RUNNER_HOME;
import static com.marvelution.bamboo.plugins.sonar.tasks.SonarRunnerCapabilityDefaultsHelper.SONAR_RUNNER_PREFIX;
import static com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarConfigConstants.CFG_SONAR_EXTRA_CUSTOM_PARAMETERS;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.atlassian.bamboo.process.EnvironmentVariableAccessor;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Helper class for the Sonar Runner Task execution configuration
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarRunnerConfig {

	private static final Logger LOGGER = Logger.getLogger(SonarRunnerConfig.class);

	private final String builderLabel;
	private final String builderPath;
	private final File workingDirectory;
	private final String environmentVariables;
	private final Map<String, String> environment = Maps.newHashMap();
	private final List<String> commandline = Lists.newArrayList();

	/**
	 * Constructor
	 * 
	 * @param taskContext the {@link TaskContext}
	 * @param capabilityContext the {@link CapabilityContext}
	 * @param environmentVariableAccessor the {@link EnvironmentVariableAccessor} implementation
	 */
	public SonarRunnerConfig(TaskContext taskContext, CapabilityContext capabilityContext,
			EnvironmentVariableAccessor environmentVariableAccessor) {
		builderLabel = ((String) Preconditions.checkNotNull(taskContext.getConfigurationMap().get(CFG_BUILDER_LABEL),
			"Builder label is not defined"));
		builderPath = ((String) Preconditions.checkNotNull(capabilityContext.getCapabilityValue(
			SONAR_RUNNER_PREFIX + "." + builderLabel), "Builder path is not defined"));
		environmentVariables = ((String)taskContext.getConfigurationMap().get(CFG_ENVIRONMENT_VARIABLES));
		workingDirectory = taskContext.getWorkingDirectory();
		commandline.add(getSonarRunnerExecutable(builderPath));
		if(StringUtils.isNotBlank(taskContext.getConfigurationMap().get(CFG_SONAR_EXTRA_CUSTOM_PARAMETERS))) {
			commandline.add(taskContext.getConfigurationMap().get(CFG_SONAR_EXTRA_CUSTOM_PARAMETERS));
		}
		environment.putAll(environmentVariableAccessor.splitEnvironmentAssignments(environmentVariables, false));
		environment.put(SONAR_RUNNER_HOME, builderPath);
	}

	/**
	 * Get the path to the executable
	 * 
	 * @param homePath the home directory of the Sonar Runner
	 * @return the path to the Sonar Runner executable
	 */
	private String getSonarRunnerExecutable(String homePath) {
		String pathToExecutable = StringUtils.join(new String[] {homePath, "bin", SONAR_RUNNER_EXECUTABLE},
			File.separator);
		if (StringUtils.contains(pathToExecutable, " ")) {
			try {
				File f = new File(pathToExecutable);
				pathToExecutable = f.getCanonicalPath();
			} catch (IOException e) {
				LOGGER.warn("IO Exception trying to get executable", e);
			}
		}
		return pathToExecutable;
	}

	/**
	 * Getter for workingDirectory
	 * 
	 * @return the workingDirectory
	 */
	public File getWorkingDirectory() {
		return workingDirectory;
	}

	/**
	 * Getter for environment
	 * 
	 * @return the environment
	 */
	public Map<String, String> getExtraEnvironment() {
		return environment;
	}

	/**
	 * Getter for commandline
	 * 
	 * @return the commandline
	 */
	public List<String> getCommandline() {
		return commandline;
	}

}
