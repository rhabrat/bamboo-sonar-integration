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

package com.marvelution.bamboo.plugins.sonar.tasks;

import com.atlassian.bamboo.utils.SystemProperty;
import com.atlassian.bamboo.v2.build.agent.capability.AbstractHomeDirectoryCapabilityDefaultsHelper;
import com.atlassian.bamboo.v2.build.agent.capability.ExecutablePathUtils;

/**
 * {@link CapabilityDefaultsHelper} implementation for the Sonar Runner
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarRunnerCapabilityDefaultsHelper extends AbstractHomeDirectoryCapabilityDefaultsHelper {

	public static final String SONAR_RUNNER_PREFIX = CAPABILITY_BUILDER_PREFIX + ".snr";
	public static final SystemProperty SONAR_SUNNER_HOME = new SystemProperty(false, new String[] { "SONAR_RUNNER_HOME" });

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getExecutableName() {
		return getExecutable();
	}

	/**
	 * Get the default executable name
	 * 
	 * @return the executable name
	 */
	public static final String getExecutable() {
		return ExecutablePathUtils.makeBatchIfOnWindows("sonar-runner");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getEnvHome() {
		return SONAR_SUNNER_HOME.getValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getPosixHome() {
		return "/usr/share/sonar/";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getCapabilityKey() {
		return SONAR_RUNNER_PREFIX + ".Sonar Runner";
	}

}
