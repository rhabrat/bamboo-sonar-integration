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

package com.marvelution.bamboo.plugins.sonar.wsclient.unmarshallers;

import java.util.List;

import org.sonar.wsclient.services.WSUtils;
import org.sonar.wsclient.unmarshallers.AbstractUnmarshaller;

import com.google.common.collect.Lists;
import com.marvelution.bamboo.plugins.sonar.wsclient.services.Project;
import com.marvelution.bamboo.plugins.sonar.wsclient.services.Version;

/**
 * {@link AbstractUnmarshaller} implementation for the {@link Project} model
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class ProjectUnmarshaller extends AbstractUnmarshaller<Project> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Project parse(Object json) {
		Project project = new Project();
		parseProjectFields(json, project);
		parseVersions(json, project);
		setLastVersion(json, project);
		return project;
	}

	/**
	 * Internal method to parse a Project JSON to a {@link Project}
	 * 
	 * @param json the JSON object to parse
	 * @param project the {@link Project} to store the data in
	 */
	private void parseProjectFields(Object json, Project project) {
		WSUtils utils = WSUtils.getINSTANCE();
		project.setId(utils.getString(json, "id")).setKey(utils.getString(json, "k"))
			.setName(utils.getString(json, "nm")).setScope(utils.getString(json, "sc"))
			.setQualifier(utils.getString(json, "qu")).setDescription(utils.getString(json, "ds"));
	}

	/**
	 * Internal method to parse the Versions of a {@link Project}
	 * 
	 * @param json the JSON object to parse
	 * @param project the {@link Project} to store the data in
	 */
	private void parseVersions(Object json, Project project) {
		WSUtils utils = WSUtils.getINSTANCE();
		Object versionsJson = utils.getField(json, "v");
		if (versionsJson != null) {
			project.setVersions(parseVersions(versionsJson));
		}
	}

	/**
	 * Internal method to parse a {@link Version} JSON object array
	 * 
	 * @param versionsJson the {@link Version} JSON object array
	 * @return the {@link List} {@link Version} objects
	 */
	private List<Version> parseVersions(Object versionsJson) {
		WSUtils utils = WSUtils.getINSTANCE();
		List<Version> versions = Lists.newArrayList();
		for (String versionName : utils.getFields(versionsJson)) {
			Object versionJson = utils.getField(versionsJson, versionName);
			if (versionJson != null) {
				Version version = new Version();
				version.setSid(utils.getString(versionJson, "sid")).setDate(utils.getDateTime(versionJson, "d"))
					.setLast(false).setName(versionName);
				versions.add(version);
			}
		}
		return versions;
	}

	/**
	 * Set the Last version boolean marker
	 * 
	 * @param json the JSON to parse
	 * @param project the {@link Project} to set the last marker
	 */
	private void setLastVersion(Object json, Project project) {
		WSUtils utils = WSUtils.getINSTANCE();
		String lastVersion = utils.getString(json, "lv");
		if (lastVersion != null && !project.getVersions().isEmpty()) {
			for (Version version : project.getVersions()) {
				if (version.getName().equals(lastVersion)) {
					version.setLast(true);
					break;
				}
			}
		}
	}

}
