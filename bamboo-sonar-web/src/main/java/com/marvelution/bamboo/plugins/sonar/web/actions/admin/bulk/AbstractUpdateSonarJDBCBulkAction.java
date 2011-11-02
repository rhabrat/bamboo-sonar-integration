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

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.ww2.actions.admin.bulk.BulkAction;
import com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarTaskConfigurator;

/**
 * Base {@link BulkAction} for the JDBC related actions
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@SuppressWarnings("unchecked")
public abstract class AbstractUpdateSonarJDBCBulkAction extends AbstractSonarBulkAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AbstractUpdateSonarJDBCBulkAction.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validateTaskConfiguration(SonarTaskConfigurator taskConfigurator, ActionParametersMap params,
					ErrorCollection errorCollection) {
		taskConfigurator.validateSonarServer(params, errorCollection);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getTaskConfigurationMap(Map<String, String[]> params) {
		Map<String, String> config = super.getTaskConfigurationMap(params);
		config.put(CFG_SONAR_JDBC_URL, getNewUrl(params));
		config.put(CFG_SONAR_JDBC_USERNAME, getNewUsername(params));
		config.put(CFG_SONAR_JDBC_PASSWORD, getNewPassword(params));
		config.put(CFG_SONAR_JDBC_DRIVER, getNewDriver(params));
		return config;
	}

	/**
	 * Get the new Sonar JDBC url
	 * 
	 * @param params the submitted parameters
	 * @return the new JDBC url, may be <code>empty</code>
	 */
	public String getNewUrl(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_JDBC_URL) != null) {
			LOGGER.debug("New " + CFG_SONAR_JDBC_URL + " is set");
			return ((String[])params.get(CFG_SONAR_JDBC_URL))[0];
		}
		LOGGER.debug("New " + CFG_SONAR_JDBC_URL + " is not set");
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
			LOGGER.debug("New " + CFG_SONAR_JDBC_DRIVER + " is set");
			return ((String[])params.get(CFG_SONAR_JDBC_DRIVER))[0];
		}
		LOGGER.debug("New " + CFG_SONAR_JDBC_DRIVER + " is not set");
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
			LOGGER.debug("New " + CFG_SONAR_JDBC_USERNAME + " is set");
			return ((String[])params.get(CFG_SONAR_JDBC_USERNAME))[0];
		}
		LOGGER.debug("New " + CFG_SONAR_JDBC_USERNAME + " is not set");
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
			LOGGER.debug("New " + CFG_SONAR_JDBC_PASSWORD + " is set");
			return ((String[])params.get(CFG_SONAR_JDBC_PASSWORD))[0];
		}
		LOGGER.debug("New " + CFG_SONAR_JDBC_PASSWORD + " is not set");
		return "";
	}

}
