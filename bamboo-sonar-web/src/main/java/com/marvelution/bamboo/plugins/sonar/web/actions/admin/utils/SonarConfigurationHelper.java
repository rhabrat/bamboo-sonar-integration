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

package com.marvelution.bamboo.plugins.sonar.web.actions.admin.utils;

import static com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarConfigConstants.*;

import org.apache.log4j.Logger;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.task.TaskDefinition;

/**
 * {@link TaskDefinition} utility class
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarConfigurationHelper {

	private static final Logger LOGGER = Logger.getLogger(SonarConfigurationHelper.class);

	private final Job job;
	private final TaskDefinition taskDefinition;

	/**
	 * Default Constructor
	 * 
	 * @param job the {@link Job}
	 * @param taskDefinition the {@link TaskDefinition}
	 */
	public SonarConfigurationHelper(Job job, TaskDefinition taskDefinition) {
		this.job = job;
		this.taskDefinition = taskDefinition;
	}

	/**
	 * Get the Host url
	 * 
	 * @return the Host url
	 */
	public String getHostUrl() {
		return getField(CFG_SONAR_HOST_URL);
	}

	/**
	 * Get the Host username
	 * 
	 * @return the host username
	 */
	public String getHostUsername() {
		return getField(CFG_SONAR_HOST_USERNAME);
	}

	/**
	 * Get the Host password
	 * 
	 * @return the Host password
	 */
	public String getHostPassword() {
		return getField(CFG_SONAR_HOST_PASSWORD);
	}

	/**
	 * Get the JDBC url
	 * 
	 * @return the JDBC url
	 */
	public String getJdbcUrl() {
		return getField(CFG_SONAR_JDBC_URL);
	}

	/**
	 * Get the JDBC username
	 * 
	 * @return the JDBC username
	 */
	public String getJdbcUsername() {
		return getField(CFG_SONAR_JDBC_USERNAME);
	}

	/**
	 * Get the JDBC password
	 * 
	 * @return the JDBC password
	 */
	public String getJdbcPassword() {
		return getField(CFG_SONAR_JDBC_PASSWORD);
	}

	/**
	 * Get the JDBC Driver
	 * 
	 * @return the JDBC Driver
	 */
	public String getJdbcDriver() {
		return getField(CFG_SONAR_JDBC_DRIVER);
	}

	/**
	 * Get the Server Configured field
	 * 
	 * @return the Server Configured field
	 */
	public Boolean getServerConfigured() {
		return Boolean.valueOf(getField(CFG_SONAR_SERVER_CONFIGURED));
	}

	/**
	 * Get the Sonar Plugin Pre Installed field
	 * 
	 * @return the Plugin Pre Installed field
	 */
	public Boolean getPluginPreInstalled() {
		return Boolean.valueOf(getField(CFG_SONAR_PLUGIN_PREINSTALLED));
	}

	/**
	 * Get the JDBC Profile
	 * 
	 * @return the JDBC Profile
	 */
	public String getJdbcProfile() {
		return getField(CFG_SONAR_JDBC_PROFILE);
	}

	private final String getField(String field) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Getting field " + field + " with value: " + taskDefinition.getConfiguration().get(field));
		}
		return taskDefinition.getConfiguration().get(field);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SonarConfigurationHelper) {
			SonarConfigurationHelper other = (SonarConfigurationHelper) obj;
			return job.getBuildKey().equals(other.job.getBuildKey())
				&& taskDefinition.getId() == other.taskDefinition.getId();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return job.getBuildKey().hashCode() + taskDefinition.getPluginKey().hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "SCH for " + job.getBuildKey() + " and Task " + taskDefinition.getId();
	}

}
