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

package com.marvelution.bamboo.plugins.sonar.tasks.actions.admin.bulk;

import static com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarConfigConstants.*;

import java.util.Map;

import org.apache.log4j.Logger;

import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.ww2.actions.admin.bulk.BulkAction;
import com.marvelution.bamboo.plugins.sonar.tasks.predicates.SonarPredicates;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarTaskUtils;

/**
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@SuppressWarnings("unchecked")
public class UpdateSonarMavenJDBCProfileBulkAction extends AbstractSonarBulkAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UpdateSonarMavenJDBCProfileBulkAction.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getKey() {
		return "bulk.action.sonar.maven.jdbc.profile.update";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return getText("bulkAction.sonarJdbcProfile.title");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getChangedItem() {
		return getText("bulkAction.sonarJdbcProfile.changeItem");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getViewAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "viewSonarJdbcProfile");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getViewUpdatedAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "viewUpdatedSonarJdbcProfile");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getEditSnippetAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "editSonarJdbcProfile");
	}

	/**
	 * Get the new Sonar JDBC Profile
	 * 
	 * @param params the submitted parameters
	 * @return the new JDBC profile, may be <code>empty</code>
	 */
	public String getNewProfile(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_JDBC_PROFILE) != null) {
			LOGGER.debug("Found new Sonar JDBC Profile");
			return ((String[])params.get(CFG_SONAR_JDBC_PROFILE))[0];
		}
		LOGGER.debug("New Sonar JDBC Profile not found");
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isApplicable(Plan plan) {
		return SonarTaskUtils.hasSonarTasks(plan, SonarPredicates.isSonarMavenTask());
	}

}
