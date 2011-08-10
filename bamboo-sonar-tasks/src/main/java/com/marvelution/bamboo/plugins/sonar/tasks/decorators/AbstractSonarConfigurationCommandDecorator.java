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
import java.util.Map.Entry;

import org.jetbrains.annotations.NotNull;

import com.atlassian.bamboo.task.TaskContext;
import com.atlassian.bamboo.task.plugins.TaskProcessCommandDecorator;
import com.google.common.collect.Lists;

/**
 * Abstract {@link TaskProcessCommandDecorator} implementation to add properties to the command
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public abstract class AbstractSonarConfigurationCommandDecorator implements TaskProcessCommandDecorator {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NotNull
	public List<String> decorate(@NotNull TaskContext taskContext, @NotNull List<String> command) {
		List<String> decoratedCommand = Lists.newArrayList(command);
		Map<String, String> properties = getCommandProperties(taskContext);
		for (Entry<String, String> property : properties.entrySet()) {
			addPropertyToCommand(decoratedCommand, property.getKey(), property.getValue());
		}
		return decoratedCommand;
	}

	/**
	 * Internal method to get all the command properties in a key/value {@link Map}
	 * 
	 * @param taskContext the {@link TaskContext} to get the proeprties from
	 * @return the {@link Map} of properties
	 */
	protected abstract Map<String, String> getCommandProperties(@NotNull TaskContext taskContext);

	/**
	 * Internal method to add a Property to the Task Command
	 * 
	 * @param command the current command {@link List} to add the property to
	 * @param key the property key
	 * @param value the property value
	 */
	protected abstract void addPropertyToCommand(@NotNull List<String> command, @NotNull String key, String value);

}
