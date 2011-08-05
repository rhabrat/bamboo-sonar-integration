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

package com.marvelution.bamboo.plugins.sonar.tasks.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Sonar Helper class for common methods used by the task types
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarHelper {

	/**
	 * Add a single property key value pair to the command {@link List}
	 * 
	 * @param command the Command {@link List} to add the property to
	 * @param key the key of the property
	 * @param value the value of the property, if <code>null</code> then the value will be <code>true</code>
	 */
	public static void addPropertyToCommand(@NotNull List<String> command, @NotNull String key, String value) {
		if (StringUtils.isBlank(value)) {
			value = String.valueOf(Boolean.TRUE);
		}
		command.add(1, String.format("--define %s=%s", new Object[] { key, value }));
	}

	/**
	 * Add a {@link Map} of properties to the command {@link List}
	 * 
	 * @param command the Command {@link List} to add the properties to
	 * @param properties the {@link Map} of properties to add
	 */
	public static void addPropertiesToCommand(@NotNull List<String> command, Map<String, String> properties) {
		for (Entry<String, String> property : properties.entrySet()) {
			addPropertyToCommand(command, property.getKey(), property.getValue());
		}
	}

}
