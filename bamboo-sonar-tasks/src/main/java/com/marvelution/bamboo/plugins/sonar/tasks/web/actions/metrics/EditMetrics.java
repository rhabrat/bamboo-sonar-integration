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

package com.marvelution.bamboo.plugins.sonar.tasks.web.actions.metrics;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.atlassian.bamboo.labels.LabelParser;
import com.atlassian.bamboo.security.acegi.acls.BambooPermission;
import com.atlassian.bamboo.ww2.actions.PlanActionSupport;
import com.atlassian.bamboo.ww2.aware.permissions.PlanReadSecurityAware;
import com.marvelution.bamboo.plugins.sonar.tasks.web.metrics.SonarMetricsManager;

/**
 * {@link PlanActionSupport} implementation for Editing the Metrics used
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@SuppressWarnings("unchecked")
public class EditMetrics extends PlanActionSupport implements PlanReadSecurityAware {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(EditMetrics.class);

	private SonarMetricsManager sonarMetricsManager;
	private String metricInput;
	private String selectedMetric;
	private List<String> metrics;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String doDefault() throws Exception {
		return INPUT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validate() {
		if (getPlan() != null) {
			return;
		}
		addActionError(getText("sonar.metrics.no.plan.or.build"));
	}

	/**
	 * Method to add metrics to the configuration
	 * 
	 * @return the view name
	 */
	public String doAddMetrics() {
		if (!bambooPermissionManager.hasPlanPermission(BambooPermission.WRITE.toString(), getPlan().getKey())) {
			addActionError("Cannot add Metrics. You need edit permission to plan " + getPlan().getName());
		}
		List<String> newMetrics = LabelParser.split(getMetricInput());
		for (String metric : newMetrics) {
			// TODO Validate against Sonar
			// We can use the LabelParser to validate the Sonar Metric
			if (!LabelParser.isValidLabelName(metric)) {
				addFieldError("metricInput",
					metric + " contains invalid characters " + LabelParser.getInvalidCharactersAsString());
			}
		}
		if (hasAnyErrors()) {
			return ERROR;
		}
		List<String> metrics = getMetrics();
		for (String metric : newMetrics) {
			LOGGER.debug("Adding new metric: " + metric);
			metrics.add(metric);
		}
		sonarMetricsManager.setTimeMachineMetrics(getPlan(), getUser(), metrics);
		return SUCCESS;
	}

	/**
	 * Method to delete a metric from the configuration
	 * 
	 * @return the view name
	 */
	public String doDeleteMetric() {
		if (!bambooPermissionManager.hasPlanPermission(BambooPermission.WRITE.toString(), getPlan().getKey())) {
			addActionError("Cannot add Metrics. You need edit permission to plan " + getPlan().getName());
		}
		if (StringUtils.isBlank(getSelectedMetric())) {
			addActionError("Cannot find the metric to delete");
		}
		if (hasAnyErrors()) {
			return ERROR;
		}
		LOGGER.debug("Deleting metric: " + getSelectedMetric());
		List<String> metrics = getMetrics();
		metrics.remove(getSelectedMetric());
		sonarMetricsManager.setTimeMachineMetrics(getPlan(), getUser(), metrics);
		return SUCCESS;
	}

	/**
	 * Getter for metricInput
	 * 
	 * @return the metricInput
	 */
	public String getMetricInput() {
		return metricInput;
	}

	/**
	 * Setter for metricInput
	 * 
	 * @param metricInput the metricInput to set
	 */
	public void setMetricInput(String metricInput) {
		this.metricInput = metricInput;
	}

	/**
	 * Getter for selectedMetric
	 * 
	 * @return the selectedMetric
	 */
	public String getSelectedMetric() {
		return selectedMetric;
	}

	/**
	 * Setter for selectedMetric
	 * 
	 * @param selectedMetric the selectedMetric to set
	 */
	public void setSelectedMetric(String selectedMetric) {
		this.selectedMetric = selectedMetric;
	}

	/**
	 * Getter for metrics
	 * 
	 * @return the metrics
	 */
	public List<String> getMetrics() {
		if (metrics == null) {
			metrics = sonarMetricsManager.getTimeMachineChartMetrics(getPlan(), getUser());
		}
		return metrics;
	}

	/**
	 * Setter for metrics
	 * 
	 * @param metrics the metrics to set
	 */
	public void setMetrics(List<String> metrics) {
		this.metrics = metrics;
	}

	/**
	 * Getter for sonarMetricsManager
	 * 
	 * @return the sonarMetricsManager
	 */
	public SonarMetricsManager getSonarMetricsManager() {
		return sonarMetricsManager;
	}

	/**
	 * Setter for sonarMetricsManager
	 * 
	 * @param sonarMetricsManager the sonarMetricsManager to set
	 */
	public void setSonarMetricsManager(SonarMetricsManager sonarMetricsManager) {
		this.sonarMetricsManager = sonarMetricsManager;
	}

}
