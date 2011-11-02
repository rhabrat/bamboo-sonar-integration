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

import org.jetbrains.annotations.NotNull;

/**
 * Helper class for Sonar Maven related configuration/execution
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarMavenHelper {

	/**
	 * Utility method to add a property to the command of Sonar Maven command
	 * 
	 * @param command the command {@link List} of existing arguments, may not be <code>null</code>
	 * @param key the property key
	 * @param value the property value
	 * @return the new command {@link List}
	 */
	public static List<String> addPropertyToCommand(@NotNull List<String> command, @NotNull String key,
					@NotNull String value) {
		command.add(1, String.format("-D%s=%s", new Object[] {key, value}));
		return command;
	}

}
