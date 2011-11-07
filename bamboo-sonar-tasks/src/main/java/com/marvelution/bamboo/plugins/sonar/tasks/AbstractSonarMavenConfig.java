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

import static com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarConfigConstants.*;

import com.atlassian.bamboo.process.CommandlineStringUtils;
import com.atlassian.bamboo.process.EnvironmentVariableAccessor;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract Maven Configuration helper.
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public abstract class AbstractSonarMavenConfig {

	private static final Logger log = Logger.getLogger(AbstractSonarMavenConfig.class);
	private static final String CFG_ENVIRONMENT_VARIABLES = "environmentVariables";
	private static final String CFG_HAS_TESTS = "testChecked";
	private static final String CFG_PROJECT_FILENAME = "projectFile";
	private static final String CFG_TEST_RESULTS_FILE_PATTERN = "testResultsDirectory";

	public static final String CFG_GOALS = "goal";

	private final String executableName;
	private final boolean hasTests;
	private final String testResultsFilePattern;
	private final File workingDirectory;
	private final List<String> commandline = Lists.newArrayList();

	protected final Map<String, String> extraEnvironment = Maps.newHashMap();
	protected String builderPath;

	/**
	 * Constructor
	 *
	 * @param taskContext the {@link TaskContext} of the current executing taks
	 * @param capabilityContext the {@link CapabilityContext}
	 * @param environmentVariableAccessor the {@link EnvironmentVariableAccessor} implementation
	 * @param capabilityPrefix the capability prefix of the Maven executable
	 * @param executableName the executable name
	 */
	public AbstractSonarMavenConfig(@NotNull TaskContext taskContext, @NotNull CapabilityContext capabilityContext,
									@NotNull EnvironmentVariableAccessor environmentVariableAccessor,
									@NotNull String capabilityPrefix, @NotNull String executableName) {
		this.executableName = executableName;
		String builderLabel =
			(String) Preconditions.checkNotNull(taskContext.getConfigurationMap().get("label"),
				"Builder label is not defined");
		this.builderPath =
			((String) Preconditions.checkNotNull(
				capabilityContext.getCapabilityValue(capabilityPrefix + "." + builderLabel),
				"Builder path is not defined"));
		String environmentVariables = taskContext.getConfigurationMap().get(CFG_ENVIRONMENT_VARIABLES);
		StringBuilder rawGoals = new StringBuilder();
		if (taskContext.getConfigurationMap().getAsBoolean(CFG_SONAR_PLUGIN_PREINSTALLED)) {
			rawGoals.append(SONAR_PLUGIN_GOAL);
		} else {
			rawGoals.append(SONAR_PLUGIN_GROUPID).append(":").append(SONAR_PLUGIN_ARTIFACTID).append(":")
				.append(getSonarMavenPluginVersion());
		}
		rawGoals.append(":").append(SONAR_PLUGIN_GOAL);
		if (StringUtils.isNotBlank(taskContext.getConfigurationMap().get(CFG_SONAR_EXTRA_CUSTOM_PARAMETERS))) {
			rawGoals.append(" ").append(taskContext.getConfigurationMap().get(CFG_SONAR_EXTRA_CUSTOM_PARAMETERS));
		}
		List<String> goals = CommandlineStringUtils.tokeniseCommandline(rawGoals.toString());
		this.hasTests = taskContext.getConfigurationMap().getAsBoolean(CFG_HAS_TESTS);
		String projectFilename = taskContext.getConfigurationMap().get(CFG_PROJECT_FILENAME);
		this.testResultsFilePattern = taskContext.getConfigurationMap().get(CFG_TEST_RESULTS_FILE_PATTERN);
		this.workingDirectory = taskContext.getWorkingDirectory();
		this.commandline.add(getMavenExecutablePath(this.builderPath));
		if (StringUtils.isNotEmpty(projectFilename)) {
			this.commandline.addAll(Arrays.asList(new String[] {"-f", projectFilename}));
		}
		this.commandline.addAll(goals);
		this.extraEnvironment.putAll(environmentVariableAccessor.splitEnvironmentAssignments(environmentVariables,
			false));
	}

	/**
	 * Get the Maven executable path from the home path of the Maven installation
	 * 
	 * @param homePath the home path (M2_HOME) of the Maven installation
	 * @return the path to the executable
	 */
	@NotNull
	protected String getMavenExecutablePath(@NotNull String homePath) {
		String pathToExecutable = StringUtils.join(new String[] {homePath, "bin", this.executableName},
			File.separator);
		if (StringUtils.contains(pathToExecutable, " ")) {
			try {
				File f = new File(pathToExecutable);
				pathToExecutable = f.getCanonicalPath();
			} catch (IOException e) {
				log.warn("IO Exception trying to get executable", e);
			}
		}
		return pathToExecutable;
	}

	/**
	 * Getter for {@link #commandline}
	 * 
	 * @return the {@link #commandline}
	 */
	public List<String> getCommandline() {
		return this.commandline;
	}

	/**
	 * Getter for {@link #extraEnvironment}
	 * 
	 * @return the {@link #extraEnvironment}
	 */
	public Map<String, String> getExtraEnvironment() {
		return this.extraEnvironment;
	}

	/**
	 * Getter for {@link #hasTests}
	 * 
	 * @return the {@link #hasTests}
	 */
	public boolean isHasTests() {
		return this.hasTests;
	}

	/**
	 * Getter for {@link #testResultsFilePattern}
	 * 
	 * @return the {@link #testResultsFilePattern}
	 */
	public String getTestResultsFilePattern() {
		return this.testResultsFilePattern;
	}

	/**
	 * Getter for {@link #workingDirectory}
	 * 
	 * @return the {@link #workingDirectory}
	 */
	public File getWorkingDirectory() {
		return this.workingDirectory;
	}

	/**
	 * Getter for the Sonar Maven Plugin version
	 * 
	 * @return the plugin version
	 */
	protected abstract String getSonarMavenPluginVersion();

}
