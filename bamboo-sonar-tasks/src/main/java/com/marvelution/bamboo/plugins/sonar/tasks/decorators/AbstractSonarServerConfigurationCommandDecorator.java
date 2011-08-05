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

package com.marvelution.bamboo.plugins.sonar.tasks.decorators;

import static com.marvelution.bamboo.plugins.sonar.tasks.configuration.AbstractSonarBuildTaskConfigurator.*;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.plugins.TaskProcessCommandDecorator;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarHelper;

/**
 * Abstract {@link TaskProcessCommandDecorator} to add Sonar Server settings to a command
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public abstract class AbstractSonarServerConfigurationCommandDecorator implements TaskProcessCommandDecorator {

	/**
	 * {@inheritDoc}
	 */
	public void addSonarServerProperties(@NotNull TaskContext taskContext, @NotNull List<String> command) {
		final ConfigurationMap configuration = taskContext.getConfigurationMap();
		SonarHelper.addPropertyToCommand(command, "sonar.host.url", configuration.get(CFG_SONAR_HOST_URL));
		SonarHelper.addPropertyToCommand(command, "sonar.jdbc.username", configuration.get(CFG_SONAR_JDBC_USERNAME));
		SonarHelper.addPropertyToCommand(command, "sonar.jdbc.password", configuration.get(CFG_SONAR_JDBC_PASSWORD));
		// Sonar JDBC URL is optional
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_JDBC_URL))) {
			SonarHelper.addPropertyToCommand(command, "sonar.jdbc.url", configuration.get(CFG_SONAR_JDBC_URL));
		}
		// Sonar JDBC Driver is optional
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_JDBC_DRIVER))) {
			SonarHelper.addPropertyToCommand(command, "sonar.jdbc.dirverClassName",
				configuration.get(CFG_SONAR_JDBC_DRIVER));
		}
	}

}
