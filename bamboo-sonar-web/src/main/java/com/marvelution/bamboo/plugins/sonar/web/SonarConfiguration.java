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

package com.marvelution.bamboo.plugins.sonar.web;

import org.apache.commons.lang.StringUtils;
import org.sonar.wsclient.Host;

/**
 * Sonar Configuration object
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarConfiguration {

	/**
	 * Sonar Server related properties
	 */
	private String host;
	private String username;
	private String password;

	/**
	 * Sonar Project related properties
	 */
	private String projectName;
	private String projectKey;
	
	/**
	 * Getter for host
	 * 
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Get the {@link Host} used for the Sonar WS Client library
	 * 
	 * @return the {@link Host}
	 */
	public Host getSonarHost() {
		Host host = new Host(this.host);
		if (isSecured()) {
			host.setUsername(username);
			host.setPassword(password);
		}
		return host;
	}
	
	/**
	 * Setter for host
	 * 
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Getter for username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Setter for username
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Getter for password
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Setter for password
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Getter for projectName
	 * 
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	
	/**
	 * Setter for projectName
	 * 
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	/**
	 * Getter for projectKey
	 * 
	 * @return the projectKey
	 */
	public String getProjectKey() {
		return projectKey;
	}
	
	/**
	 * Setter for projectKey
	 * 
	 * @param projectKey the projectKey to set
	 */
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	/**
	 * Is secured check method
	 * 
	 * @return <code>true</code> if the {@link #username} and {@link #password} fields are not blank
	 */
	public boolean isSecured() {
		return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
	}

	/**
	 * Is analyzed check method
	 * 
	 * @return <code>true</code> if the {@link #projectKey} and {@link #projectName} fields are not blank
	 */
	public boolean isAnalyzed() {
		return StringUtils.isNotBlank(projectKey) && StringUtils.isNotBlank(projectName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Sonar Config for " + getProjectKey() + " on " + getHost();
	}

}
