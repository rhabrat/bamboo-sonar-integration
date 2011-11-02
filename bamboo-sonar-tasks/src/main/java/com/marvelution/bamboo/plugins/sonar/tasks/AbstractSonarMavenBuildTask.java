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

import static com.atlassian.bamboo.task.TaskConfigConstants.CFG_PROJECT_FILENAME;

import java.io.File;
import java.io.FileReader;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.build.logger.interceptors.ErrorMemorisingInterceptor;
import com.atlassian.bamboo.build.logger.interceptors.LogMemorisingInterceptor;
import com.atlassian.bamboo.build.logger.interceptors.StringMatchingInterceptor;
import com.atlassian.bamboo.builder.MavenLogHelper;
import com.atlassian.bamboo.process.EnvironmentVariableAccessor;
import com.atlassian.bamboo.process.ExternalProcessBuilder;
import com.atlassian.bamboo.process.ProcessService;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.TaskException;
import com.atlassian.bamboo.task.TaskResult;
import com.atlassian.bamboo.task.TaskResultBuilder;
import com.atlassian.bamboo.task.TaskType;
import com.atlassian.bamboo.utils.SystemProperty;
import com.atlassian.bamboo.v2.build.CurrentBuildResult;
import com.atlassian.bamboo.v2.build.agent.capability.CapabilityContext;
import com.atlassian.utils.process.ExternalProcess;
import com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarConfigConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract Sonar {@link TaskType} implementation for use with Maven 2 or 3
 * This reuses the Maven 2 and Maven 3 implementations provided by Atlassian
 * 
 * @param <CONFIG> the Type extending {@link AbstractSonarMavenConfig}
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public abstract class AbstractSonarMavenBuildTask<CONFIG extends AbstractSonarMavenConfig> implements TaskType {

	private static final String BUILD_SUCCESSFUL_MARKER = SystemProperty.BUILD_SUCCESSFUL_MARKER
		.getValue("BUILD SUCCESS");
	private static final boolean SEARCH_BUILD_SUCCESS_FAIL_MESSAGE_EVERYWHERE =
		SystemProperty.SEARCH_BUILD_SUCCESS_FAIL_MESSAGE_EVERYWHERE.getValue(false);
	private static final int LINES_TO_PARSE_FOR_ERRORS = 200;
	private static final int FIND_SUCCESS_MESSAGE_IN_LAST = SystemProperty.FIND_SUCCESS_MESSAGE_IN_LAST.getValue(250);

	protected final CapabilityContext capabilityContext;
	protected final EnvironmentVariableAccessor environmentVariableAccessor;
	protected final ProcessService processService;

	/**
	 * Default Constructor
	 * 
	 * @param capabilityContext the {@link CapabilityContext} implementation
	 * @param environmentVariableAccessor the {@link EnvironmentVariableAccessor} implementation
	 * @param processService the {@link ProcessService} implementation
	 */
	public AbstractSonarMavenBuildTask(CapabilityContext capabilityContext,
		EnvironmentVariableAccessor environmentVariableAccessor, ProcessService processService) {
		this.capabilityContext = capabilityContext;
		this.environmentVariableAccessor = environmentVariableAccessor;
		this.processService = processService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NotNull
	public TaskResult execute(@NotNull TaskContext taskContext) throws TaskException {
		final BuildLogger buildLogger = taskContext.getBuildLogger();
		final CurrentBuildResult currentBuildResult = taskContext.getBuildContext().getBuildResult();

		CONFIG config = getMavenConfiguration(taskContext);

		StringMatchingInterceptor buildSuccessMatcher =
			new StringMatchingInterceptor(BUILD_SUCCESSFUL_MARKER, SEARCH_BUILD_SUCCESS_FAIL_MESSAGE_EVERYWHERE);
		LogMemorisingInterceptor recentLogLines = new LogMemorisingInterceptor(LINES_TO_PARSE_FOR_ERRORS);
		ErrorMemorisingInterceptor errorLines = new ErrorMemorisingInterceptor();

		buildLogger.getInterceptorStack().add(buildSuccessMatcher);
		buildLogger.getInterceptorStack().add(recentLogLines);
		buildLogger.getInterceptorStack().add(errorLines);

		try {
			ExternalProcess externalProcess = processService.executeProcess(taskContext,
					new ExternalProcessBuilder().workingDirectory(config.getWorkingDirectory())
						.env(config.getExtraEnvironment()).command(config.getCommandline()));
			if (externalProcess.getHandler().isComplete()) {
				TaskResultBuilder taskResultBuilder =
					TaskResultBuilder.create(taskContext).checkReturnCode(externalProcess)
						.checkInterceptorMatches(buildSuccessMatcher, FIND_SUCCESS_MESSAGE_IN_LAST);
				TaskResult result = taskResultBuilder.build();
				Model pom = getProjectObjectModel(getProjectFile(taskContext));
				if (pom != null) {
					final String projectKey = String.format("%s:%s", pom.getGroupId(), pom.getArtifactId());
					getLogger().info("Setting the projectKey '" + projectKey + "' in the Build Results");
					currentBuildResult.getCustomBuildData().put(SonarConfigConstants.TRD_SONAR_PROJECT_KEY,
						projectKey);
					getLogger().info("Setting the projectName '" + pom.getName() + "' in the Build Results");
					currentBuildResult.getCustomBuildData().put(SonarConfigConstants.TRD_SONAR_PROJECT_NAME,
						pom.getName());
				} else{
					getLogger().warn("Failed to get the Project Key from the Maven Project Object Model");
				}
				return result;
			}
			throw new TaskException("Failed to execute sonar command, external process not completed?");
		} catch (Exception e) {
			throw new TaskException("Failed to execute sonar task", e);
		} finally {
			currentBuildResult.addBuildErrors(errorLines.getErrorStringList());
			currentBuildResult.addBuildErrors(MavenLogHelper.parseErrorOutput(recentLogLines.getLogEntries()));
		}
	}

	/**
	 * Get the Maven project file for the given {@link TaskContext}
	 * 
	 * @param taskContext the {@link TaskContext} to use to get the project file
	 * @return the project {@link File}
	 */
	private File getProjectFile(TaskContext taskContext) {
		if (StringUtils.isNotBlank(taskContext.getConfigurationMap().get(CFG_PROJECT_FILENAME))) {
			return new File(taskContext.getWorkingDirectory(),
				taskContext.getConfigurationMap().get(CFG_PROJECT_FILENAME));
		} else {
			return new File(taskContext.getWorkingDirectory(), SonarConfigConstants.MAVEN_PROJECT_FILE);
		}
	}

	/**
	 * Get the Apache Maven {@link Model} for a given project {@link File}
	 * 
	 * @param projectFile the project {@link File} to get the {@link Model} from
	 * @return the {@link Model} read from the project {@link File}, can be <code>null</code> if exceptions occur
	 */
	private Model getProjectObjectModel(File projectFile) {
		MavenXpp3Reader reader = new MavenXpp3Reader();
		try {
			return reader.read(new FileReader(projectFile));
		} catch (Exception e) {
			getLogger().warn("Failed to read the Maven Model from file: " + projectFile.getAbsolutePath(), e);
			return null;
		}
	}

	/**
	 * Getter for the Maven Configuration used to execute the Sonar Analysis
	 * 
	 * @return the Maven Configuration
	 */
	protected abstract CONFIG getMavenConfiguration(TaskContext taskContext);

	/**
	 * Get the {@link Logger} from the sub class
	 * 
	 * @return the {@link Logger}
	 */
	protected abstract Logger getLogger();

}
