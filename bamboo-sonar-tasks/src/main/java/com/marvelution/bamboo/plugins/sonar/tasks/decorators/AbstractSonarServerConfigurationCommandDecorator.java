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

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import com.atlassian.bamboo.configuration.ConfigurationMap;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.plugins.TaskProcessCommandDecorator;
import com.google.common.collect.Maps;

/**
 * Abstract {@link TaskProcessCommandDecorator} to add Sonar Server settings to a command
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public abstract class AbstractSonarServerConfigurationCommandDecorator extends
		AbstractSonarConfigurationCommandDecorator {

	/**
	 * {@inheritDoc}
	 */
	protected Map<String, String> addSonarServerProperties(@NotNull TaskContext taskContext) {
		Map<String, String> properties = Maps.newHashMap();
		final ConfigurationMap configuration = taskContext.getConfigurationMap();
		properties.put("sonar.host.url", configuration.get(CFG_SONAR_HOST_URL));
		// Sonar JDBC Username is optional
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_JDBC_USERNAME))) {
			properties.put("sonar.jdbc.username", configuration.get(CFG_SONAR_JDBC_USERNAME));
		}
		// Sonar JDBC Password is optional
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_JDBC_PASSWORD))) {
			properties.put("sonar.jdbc.password", configuration.get(CFG_SONAR_JDBC_PASSWORD));
		}
		// Sonar JDBC URL is optional
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_JDBC_URL))) {
			properties.put("sonar.jdbc.url", configuration.get(CFG_SONAR_JDBC_URL));
		}
		// Sonar JDBC Driver is optional
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_JDBC_DRIVER))) {
			properties.put("sonar.jdbc.driverClassName", configuration.get(CFG_SONAR_JDBC_DRIVER));
		}
		return properties;
	}

}
