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

package com.marvelution.bamboo.plugins.sonar.wsclient.services;

import org.sonar.wsclient.services.Query;

/**
 * {@link Project} {@link Query} implementation
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class ProjectQuery extends Query<Project> {

	public static final String BASE_URL = "/api/projects";

	private String resourceKeyOrId = null;
	private Boolean subprojects = null;
	private Boolean views = null;
	private Boolean libs = null;
	private String[] langs = null;
	private Boolean description = null;
	private String search = null;
	private Boolean versions = null;

	/**
	 * Default Constructor
	 */
	public ProjectQuery() {
	}

	/**
	 * Constructor
	 * 
	 * @param resourceKeyOrId the resource key
	 */
	public ProjectQuery(String resourceKeyOrId) {
		this.resourceKeyOrId = resourceKeyOrId;
	}

	/**
	 * Constructor
	 * 
	 * @param resourceId the resource Id
	 */
	public ProjectQuery(long resourceId) {
		this.resourceKeyOrId = String.valueOf(resourceId);
	}

	/**
	 * Getter for resourceKeyOrId
	 * 
	 * @return the resourceKeyOrId
	 */
	public String getResourceKeyOrId() {
		return resourceKeyOrId;
	}

	/**
	 * Setter for resourceKeyOrId
	 * 
	 * @param resourceKeyOrId the resourceKeyOrId to set
	 */
	public ProjectQuery setResourceKeyOrId(String resourceKeyOrId) {
		this.resourceKeyOrId = resourceKeyOrId;
		return this;
	}

	/**
	 * Setter for resourceId
	 * 
	 * @param resourceId the resourceId to set
	 */
	public ProjectQuery setResourceKeyOrId(int resourceId) {
		this.resourceKeyOrId = Integer.toString(resourceId);
		return this;
	}

	/**
	 * Getter for subprojects
	 * 
	 * @return the subprojects
	 */
	public boolean isSubprojects() {
		return subprojects;
	}

	/**
	 * Setter for subprojects
	 * 
	 * @param subprojects the subprojects to set
	 */
	public ProjectQuery setSubprojects(boolean subprojects) {
		this.subprojects = subprojects;
		return this;
	}

	/**
	 * Getter for views
	 * 
	 * @return the views
	 */
	public boolean isViews() {
		return views;
	}

	/**
	 * Setter for views
	 * 
	 * @param views the views to set
	 */
	public ProjectQuery setViews(boolean views) {
		this.views = views;
		return this;
	}

	/**
	 * Getter for libs
	 * 
	 * @return the libs
	 */
	public boolean isLibs() {
		return libs;
	}

	/**
	 * Setter for libs
	 * 
	 * @param libs the libs to set
	 */
	public ProjectQuery setLibs(boolean libs) {
		this.libs = libs;
		return this;
	}

	
	/**
	 * Getter for langs
	 * 
	 * @return the langs
	 */
	public String[] getLangs() {
		return langs;
	}

	/**
	 * Setter for langs
	 * 
	 * @param langs the langs to set
	 */
	public ProjectQuery setLangs(String[] langs) {
		this.langs = langs;
		return this;
	}

	/**
	 * Getter for description
	 * 
	 * @return the description
	 */
	public boolean isDescription() {
		return description;
	}

	/**
	 * Setter for description
	 * 
	 * @param description the description to set
	 */
	public ProjectQuery setDescription(boolean description) {
		this.description = description;
		return this;
	}

	/**
	 * Getter for search
	 * 
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * Setter for search
	 * 
	 * @param search the search to set
	 */
	public ProjectQuery setSearch(String search) {
		this.search = search;
		return this;
	}

	/**
	 * Getter for versions
	 * 
	 * @return the versions
	 */
	public boolean isVersions() {
		return versions;
	}

	/**
	 * Setter for versions
	 * 
	 * @param versions the versions to set
	 */
	public ProjectQuery setVersions(boolean versions) {
		this.versions = versions;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUrl() {
		StringBuilder url = new StringBuilder(BASE_URL);
		url.append("?");
		appendUrlParameter(url, "key", resourceKeyOrId);
		appendUrlParameter(url, "subprojects", subprojects);
		appendUrlParameter(url, "views", views);
		appendUrlParameter(url, "libs", libs);
		appendUrlParameter(url, "langs", langs);
		appendUrlParameter(url, "desc", description);
		appendUrlParameter(url, "search", search);
		appendUrlParameter(url, "versions", versions);
		return url.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<Project> getModelClass() {
		return Project.class;
	}

}
