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

/**
 * Helper class for Sonar Runner related configuration/execution
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarRunnerHelper {

	/**
	 * Utility method to add a property to the command of Sonar Runner
	 * The Runner requires that the -D flag and the property are two cli arguments
	 * 
	 * @param command the command {@link List} of existing arguments, may not be <code>null</code>
	 * @param key the property key
	 * @param value the property value
	 */
	public static void addPropertyToCommand(List<String> command, String key, String value) {
		// First add the property itself
		command.add(1, String.format("%s=%s", key, value));
		// And then the define flag
		command.add(1, "-D");
	}

}
