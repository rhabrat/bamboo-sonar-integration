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

import java.util.List;
import java.util.Map;

import com.atlassian.bamboo.plugins.maven.utils.MavenHelper;
import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.plugins.TaskProcessCommandDecorator;
import com.google.common.collect.Maps;

/**
 * {@link TaskProcessCommandDecorator} that adds the Sonar Project properties to the Sonar Maven command if needed.
 * If the sonar.jdbc.x properties are not set but a profile is, that this {@link TaskProcessCommandDecorator} will add
 * the profile flag to the command.
 * 
 * This {@link TaskProcessCommandDecorator} is only used for:
 * <ul>
 * 	<li>{@link SonarMaven2BuildTask}</li>
 * 	<li>{@link SonarMaven3BuildTask}</li>
 * </ul>
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarMavenProjectConfigurationCommandDecorator extends
		AbstractSonarExtraProjectConfigurationCommandDecorator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void addPropertyToCommand(List<String> command, String key, String value) {
		MavenHelper.addPropertyToCommand(command, key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Map<String, String> getCommandProperties(TaskContext taskContext) {
		Map<String, String> properties = Maps.newHashMap();
		// Make sure that the Enforcer plugin doesn't run during the Sonar run.
		properties.put("enforcer.skip", "true");
		properties.putAll(addSonarExtraProjectProperties(taskContext));
		return properties;
	}

}
