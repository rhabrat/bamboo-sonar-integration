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

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import com.atlassian.bamboo.ww2.actions.admin.bulk.BulkAction;
import com.marvelution.bamboo.plugins.sonar.tasks.configuration.SonarTaskConfigurator;
import com.marvelution.bamboo.plugins.sonar.tasks.predicates.SonarPredicates;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarTaskUtils;

/**
 * Bulk Action to update the Maven Pluin Pre Installed state
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@SuppressWarnings("unchecked")
public class UpdateSonarMavenPluginBulkAction extends AbstractSonarBulkAction {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UpdateSonarMavenPluginBulkAction.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getKey() {
		return "bulk.action.sonar.maven.plugin.update";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return getText("bulkAction.sonarMavenPlugin.title");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getChangedItem() {
		return getText("bulkAction.sonarMavenPlugin.changeItem");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getViewAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "viewSonarMavenPlugin");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getViewUpdatedAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "viewUpdatedSonarMavenPlugin");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getEditSnippetAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "editSonarMavenPlugin");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validateTaskConfiguration(SonarTaskConfigurator taskConfigurator, ActionParametersMap params,
					ErrorCollection errorCollection) {
		// No validation required
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getTaskConfigurationMap(Map<String, String[]> params) {
		Map<String, String> config = super.getTaskConfigurationMap(params);
		config.put(CFG_SONAR_PLUGIN_PREINSTALLED, getNewPluginPreInstalled(params).toString());
		return config;
	}

	/**
	 * Get the new Sonar Plugin Preinstalled
	 * 
	 * @param params the submitted parameters
	 * @return the new Plugin Preinstalled, may be <code>empty</code>
	 */
	public Boolean getNewPluginPreInstalled(Map<String, String[]> params) {
		if (params.get(CFG_SONAR_PLUGIN_PREINSTALLED) != null) {
			LOGGER.debug("New " + CFG_SONAR_SERVER_CONFIGURED + " is set");
			return Boolean.TRUE;
		}
		LOGGER.debug("New " + CFG_SONAR_SERVER_CONFIGURED + " is not set");
		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isApplicable(Plan plan) {
		return SonarTaskUtils.hasSonarTasks(plan, SonarPredicates.isSonarMavenTask());
	}

}
