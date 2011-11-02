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

package com.marvelution.bamboo.plugins.sonar.web.conditions;

import java.util.List;
import java.util.Map;

import com.atlassian.bamboo.ww2.actions.admin.bulk.BulkAction;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import com.marvelution.bamboo.plugins.sonar.web.actions.admin.bulk.AbstractSonarBulkAction;

/**
 * {@link Condition} to show or hide the Bulk action link under the Sonar section
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarBulkActionsConfiguredCondition implements Condition {

	private List<BulkAction> availableBulkActions;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Map<String, String> params) throws PluginParseException {
		// Not needed for this Condition
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldDisplay(Map<String, Object> context) {
		if (availableBulkActions != null) {
			for (BulkAction action : availableBulkActions) {
				if (action instanceof AbstractSonarBulkAction) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Setter for the available Bulk Actions
	 * 
	 * @param availableBulkActions {@link List} of available bulk action
	 */
	public void setAvailableBulkActions(List<BulkAction> availableBulkActions) {
		this.availableBulkActions = availableBulkActions;
	}

}
