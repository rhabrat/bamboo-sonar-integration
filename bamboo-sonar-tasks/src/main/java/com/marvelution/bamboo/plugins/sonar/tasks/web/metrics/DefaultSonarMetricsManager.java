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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.atlassian.bamboo.bandana.PlanAwareBandanaContext;
import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bandana.BandanaManager;
import com.atlassian.user.User;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.PluginHelper;

/**
 * Default {@link SonarMetricsManager} implementation
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class DefaultSonarMetricsManager implements SonarMetricsManager {

	public static final String BASE_BANDANA_KEY = PluginHelper.getPluginKey() + ":";
	public static final String TIME_MACHINE_BASE_BANDANA_KEY = BASE_BANDANA_KEY + "SonarTimeMachineMetrics:";
	public static final String GLOBAL_TIME_MACHINE_BANDANA_KEY = TIME_MACHINE_BASE_BANDANA_KEY + "Global";
	public static final List<String> DEFAULT_METRICS = ImmutableList.of("violations_density", "complexity",
		"coverage");

	private static final Logger LOGGER = Logger.getLogger(DefaultSonarMetricsManager.class);

	private final BandanaManager bandanaManager;

	/**
	 * Constructor
	 * 
	 * @param bandanaManager the {@link BandanaManager} implementation
	 */
	public DefaultSonarMetricsManager(BandanaManager bandanaManager) {
		this.bandanaManager = bandanaManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getTimeMachineChartMetrics(Plan plan) {
		return getTimeMachineChartMetrics(plan, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTimeMachineChartMetrics(Plan plan, User user) {
		LOGGER.debug("Getting metrics for plan " + plan.getKey() + " with Id " + plan.getId() + " using key: "
			+ getTimeMachineMetricsBandanaKey(plan, user));
		List<String> metrics = (List<String>) bandanaManager.getValue(new PlanAwareBandanaContext(plan.getId()),
			getTimeMachineMetricsBandanaKey(plan, user));
		if (metrics == null) {
			metrics = Lists.newArrayList();
		}
		if (metrics.isEmpty()) {
			metrics.addAll(DEFAULT_METRICS);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Got the metrics [" + StringUtils.join(metrics, ",") + "] for Plan: "
				+ plan.getKey() + " with Id " + plan.getId());
		}
		return metrics;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTimeMachineMetrics(Plan plan, List<String> metrics) {
		setTimeMachineMetrics(plan, null, metrics);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTimeMachineMetrics(Plan plan, User user, List<String> metrics) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Storing the metrics [" + StringUtils.join(metrics, ",") + "] for Plan: " + plan.getKey()
				+ " with Id " + plan.getId() + " using key: " + getTimeMachineMetricsBandanaKey(plan, user));
		}
		bandanaManager.setValue(new PlanAwareBandanaContext(plan.getId()),
			getTimeMachineMetricsBandanaKey(plan, user), metrics);
	}

	/**
	 * Getter for the time machine metrics bandana key
	 * 
	 * @param plan the {@link Plan}
	 * @param user the logged in {@link User}
	 * @return the bandana key
	 */
	private String getTimeMachineMetricsBandanaKey(Plan plan, User user) {
		if (user == null) {
			return GLOBAL_TIME_MACHINE_BANDANA_KEY;
		} else {
			return TIME_MACHINE_BASE_BANDANA_KEY + user.getName();
		}
	}

}
