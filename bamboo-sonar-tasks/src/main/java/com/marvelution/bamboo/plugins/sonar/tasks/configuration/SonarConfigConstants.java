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

import java.util.Map;

import com.atlassian.bamboo.plugins.maven.task.AbstractMavenConfig;
import com.atlassian.bamboo.task.TaskConfigConstants;
import com.google.common.collect.ImmutableMap;

/**
 * Configuration Constants interface
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public interface SonarConfigConstants {

	/**
	 * Bulk Action marker
	 */
	String BULK_ACTION_KEY = "selectedBulkActionKey";

	/**
	 * Common Options
	 */
	String CFG_SONAR_HOST_URL = "sonarHostUrl";
	String CFG_SONAR_HOST_USERNAME = "sonarHostUsername";
	String CFG_SONAR_HOST_PASSWORD = "sonarHostPassword";

	String CTX_SONAR_FAKE_PASSWORD = "sonarFakePassword";
	String SONAR_FAKE_PASSWORD = "******";

	/**
	 * Sonar Runner specific
	 */
	String CFG_SONAR_SERVER_CONFIGURED = "serverConfigured";
	String CFG_SONAR_PROJECT_CONFIGURED = "projectConfigured";

	String CFG_SONAR_PROJECT_KEY = "sonarProjectKey";
	String CFG_SONAR_PROJECT_NAME = "sonarProjectName";
	String CFG_SONAR_PROJECT_VERSION = "sonarProjectVersion";

	String CFG_SONAR_SOURCES = "sonarSources";
	String CFG_SONAR_TESTS = "sonarTests";
	String CFG_SONAR_BINARIES = "sonarBinaries";
	String CFG_SONAR_LIBRARIES = "sonarLibraries";

	String CFG_SONAR_JDBC_URL = "sonarJdbcUrl";
	String CFG_SONAR_JDBC_USERNAME = "sonarJdbcUsername";
	String CFG_SONAR_JDBC_PASSWORD = "sonarJdbcPassword";
	String CFG_SONAR_JDBC_DRIVER = "sonarJdbcDriver";

	char DIRECTORY_SEPARATOR = ',';

	/**
	 * Maven specific configuration options
	 */
	String CFG_SONAR_JDBC_PROFILE = "sonarJdbcProfile";
	String CFG_SONAR_JDBC_OPTION = "sonarJdbcOption";
	String CFG_SONAR_JDBC_USE_PROFILE = "sonarJdbcUseProfile";
	String CFG_SONAR_JDBC_USE_FORM = "sonarJdbcUseForm";

	/**
	 * Sonar plugin groupId, artifactId and goal
	 */
	String SONAR_PLUGIN_GROUPID = "org.codehaus.mojo";
	String SONAR_PLUGIN_ARTIFACTID = "sonar-maven-plugin";
	String SONAR_PLUGIN_GOAL = "sonar";
	String SONAR_M2_PLUGIN_VERSION = "1.0-beta-2";
	String SONAR_M3_PLUGIN_VERSION = "2.0-beta-2";
	
	String CFG_SONAR_PLUGIN_PREINSTALLED = "sonarPluginPreInstalled";

	String CTX_SONAR_JDBC_OPTIONS = "sonarJdbcOptions";
	Map<String, String> CFG_SONAR_JDBC_OPTIONS = ImmutableMap.of(
		CFG_SONAR_JDBC_USE_FORM, "Specify the configuration below.",
		CFG_SONAR_JDBC_USE_PROFILE, "Get configuration from a Maven Profile."
	);
	String CTX_USES_PROFILE = "usesProfile";

	String CFG_GOALS = AbstractMavenConfig.CFG_GOALS;
	String CFG_PROJECT_FILENAME = TaskConfigConstants.CFG_PROJECT_FILENAME;

	String MAVEN_PROJECT_FILE = "pom.xml";

	/**
	 * Advanced Options
	 */
	String CFG_SONAR_LANGUAGE = "sonarLanguage";
	String CFG_SONAR_JAVA_SOURCE = "sonarJavaSource";
	String CFG_SONAR_JAVA_TARGET = "sonarJavaTarget";
	String CFG_SONAR_EXTRA_CUSTOM_PARAMETERS = "sonarExtraCustomParameters";

	/**
	 * Configuration screen Context variable names
	 */
	String CTX_UI_CONFIG_BEAN = "uiConfigBean";

	/**
	 * Task Result Data key names
	 */
	String TRD_SONAR_PROJECT_KEY = "com.marvelution.bamboo.plugins.sonar.task.project.key";
	String TRD_SONAR_PROJECT_NAME = "com.marvelution.bamboo.plugins.sonar.task.project.name";

}
