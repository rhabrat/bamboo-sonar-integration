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

package com.marvelution.bamboo.plugins.sonar.tasks.web.metrics;

import java.util.List;

import com.atlassian.bamboo.plan.Plan;
import com.atlassian.user.User;

/**
 * Manager interface for Sonar Metrics related common actions
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public interface SonarMetricsManager {

	/**
	 * Getter for the Time Machine Chart metrics
	 * 
	 * @param plan the {@link Plan} to get the metrics for
	 * @return the metrics
	 */
	List<String> getTimeMachineChartMetrics(Plan plan);

	/**
	 * Getter for the Time Machine Chart metrics
	 * 
	 * @param plan the {@link Plan} to get the metrics for
	 * @param user the logged in {@link User}
	 * @return the metrics
	 */
	List<String> getTimeMachineChartMetrics(Plan plan, User user);

	/**
	 * Setter for the Time Machine Chart metrics
	 * 
	 * @param plan the {@link Plan} to set the metrics for
	 * @param metrics the metrics to set
	 */
	void setTimeMachineMetrics(Plan plan, List<String> metrics);

	/**
	 * Setter for the Time Machine Chart metrics
	 * 
	 * @param plan the {@link Plan} to set the metrics for
	 * @param user the logged in {@link User}
	 * @param metrics the metrics to set
	 */
	void setTimeMachineMetrics(Plan plan, User user, List<String> metrics);

}
