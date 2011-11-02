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

import java.util.List;

import org.sonar.wsclient.services.Model;

import com.google.common.collect.Lists;

/**
 * Project {@link Model} implementation
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class Project extends Model {

	private Integer id;
	private String key;
	private String name;
	private String scope;
	private String description;
	private String qualifier;
	private List<Version> versions;
	
	/**
	 * Getter for id
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Setter for id
	 * 
	 * @param id the id to set
	 */
	public Project setId(Integer id) {
		this.id = id;
		return this;
	}
	
	/**
	 * Setter for id
	 * 
	 * @param id the id to set
	 */
	public Project setId(String id) {
		this.id = Integer.parseInt(id);
		return this;
	}
	
	/**
	 * Getter for key
	 * 
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Setter for key
	 * 
	 * @param key the key to set
	 */
	public Project setKey(String key) {
		this.key = key;
		return this;
	}
	
	/**
	 * Getter for name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for name
	 * 
	 * @param name the name to set
	 */
	public Project setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * Getter for scope
	 * 
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}
	
	/**
	 * Setter for scope
	 * 
	 * @param scope the scope to set
	 */
	public Project setScope(String scope) {
		this.scope = scope;
		return this;
	}
	
	/**
	 * Getter for description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter for description
	 * 
	 * @param description the description to set
	 */
	public Project setDescription(String description) {
		this.description = description;
		return this;
	}
	
	/**
	 * Getter for qualifier
	 * 
	 * @return the qualifier
	 */
	public String getQualifier() {
		return qualifier;
	}
	
	/**
	 * Setter for qualifier
	 * 
	 * @param qualifier the qualifier to set
	 */
	public Project setQualifier(String qualifier) {
		this.qualifier = qualifier;
		return this;
	}
	
	/**
	 * Getter for versions
	 * 
	 * @return the versions
	 */
	public List<Version> getVersions() {
		if (versions == null) {
			return Lists.newArrayList();
		}
		return versions;
	}

	/**
	 * Get the last {@link Version} of the project
	 * 
	 * @return the last {@link Version}, may be <code>null</code>
	 */
	public Version getLastVersion() {
		for (Version version : getVersions()) {
			if (version.isLast()) {
				return version;
			}
		}
		return null;
	}
	
	/**
	 * Setter for versions
	 * 
	 * @param versions the versions to set
	 */
	public Project setVersions(List<Version> versions) {
		this.versions = versions;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "[id=" + this.id + ",key=" + this.key + "]";
	}

}
