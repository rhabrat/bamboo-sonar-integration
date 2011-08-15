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

import com.atlassian.bamboo.ww2.actions.admin.bulk.BulkAction;

/**
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@SuppressWarnings("unchecked")
public class UpdateSonarJDBCBulkAction extends AbstractSonarBulkAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UpdateSonarJDBCBulkAction.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getKey() {
		return "bulk.action.sonar.jdbc.update";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return getText("bulkAction.sonarJdbc.title");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getChangedItem() {
		return getText("bulkAction.sonarJdbc.changeItem");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getViewAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "viewSonarJdbc");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getViewUpdatedAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "viewUpdatedSonarJdbc");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getEditSnippetAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "editSonarJdbc");
	}

	/**
	 * Get the new Sonar JDBC url
	 * 
	 * @param params the submitted parameters
	 * @return the new JDBC url, may be <code>empty</code>
	 */
	public String getNewUrl(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_JDBC_URL) != null) {
			LOGGER.debug("Found new Sonar JDBC URL");
			return ((String[])params.get(CFG_SONAR_JDBC_URL))[0];
		}
		LOGGER.debug("New Sonar JDBC URL not found");
		return "";
	}

	/**
	 * Get the new Sonar JDBC Driver
	 * 
	 * @param params the submitted parameters
	 * @return the new JDBC Driver, may be <code>empty</code>
	 */
	public String getNewDriver(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_JDBC_DRIVER) != null) {
			LOGGER.debug("Found new Sonar JDBC Driver");
			return ((String[])params.get(CFG_SONAR_JDBC_DRIVER))[0];
		}
		LOGGER.debug("New Sonar JDBC Driver not found");
		return "";
	}

	/**
	 * Get the new Sonar JDBC Username
	 * 
	 * @param params the submitted parameters
	 * @return the new JDBC username, may be <code>empty</code>
	 */
	public String getNewUsername(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_JDBC_USERNAME) != null) {
			LOGGER.debug("Found new Sonar JDBC Username");
			return ((String[])params.get(CFG_SONAR_JDBC_USERNAME))[0];
		}
		LOGGER.debug("New Sonar JDBC Username not found");
		return "";
	}

	/**
	 * Get the new Sonar JDBC Password
	 * 
	 * @param params the submitted parameters
	 * @return the new JDBC Password, may be <code>empty</code>
	 */
	public String getNewPassword(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_JDBC_PASSWORD) != null) {
			LOGGER.debug("Found new Sonar JDBC Password");
			return ((String[])params.get(CFG_SONAR_JDBC_PASSWORD))[0];
		}
		LOGGER.debug("New Sonar JDBC Password not found");
		return "";
	}

}
