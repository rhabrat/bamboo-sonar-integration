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
public abstract class AbstractSonarExtraProjectConfigurationCommandDecorator extends
		AbstractSonarConfigurationCommandDecorator {

	/**
	 * {@inheritDoc}
	 */
	protected Map<String, String> addSonarExtraProjectProperties(@NotNull TaskContext taskContext) {
		Map<String, String> properties = Maps.newHashMap();
		final ConfigurationMap configuration = taskContext.getConfigurationMap();
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_LANGUAGE))) {
			properties.put("sonar.language", configuration.get(CFG_SONAR_LANGUAGE));
		}
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_JAVA_SOURCE))) {
			properties.put("sonar.java.source", configuration.get(CFG_SONAR_JAVA_SOURCE));
		}
		if (StringUtils.isNotBlank(configuration.get(CFG_SONAR_JAVA_TARGET))) {
			properties.put("sonar.java.target", configuration.get(CFG_SONAR_JAVA_TARGET));
		}
		return properties;
	}

}
