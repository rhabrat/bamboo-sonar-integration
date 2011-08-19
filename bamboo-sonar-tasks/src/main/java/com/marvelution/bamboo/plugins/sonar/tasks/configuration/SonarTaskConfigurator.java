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

package com.marvelution.bamboo.plugins.sonar.tasks.configuration;

import org.jetbrains.annotations.NotNull;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.TaskConfigurator;
import com.atlassian.bamboo.utils.error.ErrorCollection;

/**
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public interface SonarTaskConfigurator extends TaskConfigurator {

	/**
	 * Validation method to validate the Sonar Host configuration
	 * 
	 * @param params the {@link ActionParametersMap}
	 * @param errorCollection the {@link ErrorCollection}
	 */
	void validateSonarHost(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection);

	/**
	 * Validation method to validate the Sonar Server configuration
	 * 
	 * @param params the {@link ActionParametersMap}
	 * @param errorCollection the {@link ErrorCollection}
	 */
	void validateSonarServer(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection);

	/**
	 * Validation method to validate the Sonar Project configuration
	 * 
	 * @param params the {@link ActionParametersMap}
	 * @param errorCollection the {@link ErrorCollection}
	 */
	void validateSonarProject(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection);

	/**
	 * Validation method to validate the Sonar Advanced Properties configuration
	 * 
	 * @param params the {@link ActionParametersMap}
	 * @param errorCollection the {@link ErrorCollection}
	 */
	void validateAdvancedProperties(@NotNull ActionParametersMap params, @NotNull ErrorCollection errorCollection);

}
