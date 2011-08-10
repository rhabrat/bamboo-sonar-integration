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

import static com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarConfigConstants.*;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.plugins.TaskProcessCommandDecorator;
import com.google.common.collect.Maps;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarRunnerHelper;

/**
 * {@link TaskProcessCommandDecorator} that adds the Sonar Project properties to the Sonar Runner command if needed.
 * If the sonar.jdbc.x properties are not set but a profile is, that this {@link TaskProcessCommandDecorator} will add
 * the profile flag to the command.
 * 
 * This {@link TaskProcessCommandDecorator} is only used for:
 * <ul>
 * 	<li>{@link SonarRunnerBuildTask}</li>
 * </ul>
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarRunnerProjectConfigurationCommandDecorator extends
		AbstractSonarExtraProjectConfigurationCommandDecorator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void addPropertyToCommand(List<String> command, String key, String value) {
		SonarRunnerHelper.addPropertyToCommand(command, key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, String> getCommandProperties(TaskContext taskContext) {
		Map<String, String> properties = Maps.newHashMap();
		final ConfigurationMap configuration = taskContext.getConfigurationMap();
		if (!configuration.getAsBoolean(CFG_SONAR_PROJECT_CONFIGURED)) {
			properties.put("sonar.projectKey", configuration.get(CFG_SONAR_PROJECT_KEY));
			properties.put("sonar.projectName", configuration.get(CFG_SONAR_PROJECT_NAME));
			properties.put("sonar.projectVersion", configuration.get(CFG_SONAR_PROJECT_VERSION));
			properties.put("sources", configuration.get(CFG_SONAR_SOURCES));
			if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_TESTS))) {
				properties.put("tests", configuration.get(CFG_SONAR_TESTS));
			}
			if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_BINARIES))) {
				properties.put("binaries", configuration.get(CFG_SONAR_BINARIES));
			}
			if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_LIBRARIES))) {
				properties.put("libraries", configuration.get(CFG_SONAR_LIBRARIES));
			}
		}
		properties.putAll(addSonarExtraProjectProperties(taskContext));
		return properties;
	}

}
