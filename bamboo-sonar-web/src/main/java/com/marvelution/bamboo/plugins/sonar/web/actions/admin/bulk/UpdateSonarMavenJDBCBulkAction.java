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

package com.marvelution.bamboo.plugins.sonar.web.actions.admin.bulk;

import static com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarConfigConstants.*;

import java.util.Map;

import org.apache.log4j.Logger;

import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.ww2.actions.admin.bulk.BulkAction;
import com.marvelution.bamboo.plugins.sonar.tasks.predicates.SonarPredicates;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarTaskUtils;

/**
 * Sonar Maven JDBC {@link BulkAction}
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@SuppressWarnings("unchecked")
public class UpdateSonarMavenJDBCBulkAction extends AbstractUpdateSonarJDBCBulkAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UpdateSonarMavenJDBCBulkAction.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getKey() {
		return "bulk.action.sonar.maven.jdbc.update";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return getText("bulkAction.sonarMavenJdbc.title");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getChangedItem() {
		return getText("bulkAction.sonarMavenJdbc.changeItem");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getViewAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "viewSonarMavenJdbc");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getViewUpdatedAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "viewUpdatedSonarMavenJdbc");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getEditSnippetAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "editSonarMavenJdbc");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getTaskConfigurationMap(Map<String, String[]> params) {
		Map<String, String> config = super.getTaskConfigurationMap(params);
		config.put(CFG_SONAR_JDBC_PROFILE, getNewProfile(params));
		config.put(CFG_SONAR_JDBC_OPTION, getNewJdbcOption(params));
		return config;
	}

	/**
	 * Get the new Sonar JDBC Profile
	 * 
	 * @param params the submitted parameters
	 * @return the new JDBC profile, may be <code>empty</code>
	 */
	public String getNewProfile(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_JDBC_PROFILE) != null) {
			LOGGER.debug("New " + CFG_SONAR_JDBC_PROFILE + " is set");
			return ((String[])params.get(CFG_SONAR_JDBC_PROFILE))[0];
		}
		LOGGER.debug("New " + CFG_SONAR_JDBC_PROFILE + " is not set");
		return "";
	}

	/**
	 * Get the new Sonar JDBC Option
	 * 
	 * @param params the submitted parameters
	 * @return the new JDBC Option, may be <code>empty</code>
	 */
	public String getNewJdbcOption(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_JDBC_OPTION) != null) {
			LOGGER.debug("New " + CFG_SONAR_JDBC_OPTION + " is set");
			return ((String[])params.get(CFG_SONAR_JDBC_OPTION))[0];
		}
		LOGGER.debug("New " + CFG_SONAR_JDBC_OPTION + " is not set");
		return "";
	}

	/**
	 * Get the available JDBC options
	 * 
	 * @return the {@link Map} of options
	 */
	public Map<String, String> getJdbcOptions() {
		return CFG_SONAR_JDBC_OPTIONS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isApplicable(Plan plan) {
		return SonarTaskUtils.hasSonarTasks(plan, SonarPredicates.isSonarMavenTask());
	}

}
