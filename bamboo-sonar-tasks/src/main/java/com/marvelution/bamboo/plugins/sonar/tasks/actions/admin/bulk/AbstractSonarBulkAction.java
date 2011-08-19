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

import java.util.Map;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.ww2.BambooActionSupport;
import com.atlassian.bamboo.ww2.actions.admin.bulk.BulkAction;
import com.google.common.collect.Maps;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarTaskUtils;

/**
 * Base {@link BulkAction} implementation for all Sonar Bulk Actions tasks
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@SuppressWarnings("unchecked")
public abstract class AbstractSonarBulkAction extends BambooActionSupport implements SonarBulkAction {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isApplicableForJobs() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isApplicable(Plan plan) {
		return SonarTaskUtils.hasSonarTasks(plan);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasUpdates() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateActionParameters(ActionParametersMap actionParameters, Plan plan) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getResultAction() {
		return getViewAction();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebWorkAction getExecuteAction() {
		return new BulkAction.WebWorkActionImpl("/admin/sonar", "updateSonarTask");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getTaskConfigurationMap(Map<String, String[]> params) {
		return Maps.newHashMap();
	}

}
