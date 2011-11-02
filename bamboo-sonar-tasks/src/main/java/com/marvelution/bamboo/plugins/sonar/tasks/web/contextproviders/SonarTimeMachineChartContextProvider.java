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

package com.marvelution.bamboo.plugins.sonar.tasks.web.contextproviders;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;

import com.atlassian.bamboo.build.Buildable;
import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.chains.Chain;
import com.atlassian.bamboo.user.BambooAuthenticationContext;
import com.atlassian.user.User;
import com.google.common.collect.Lists;
import com.marvelution.bamboo.plugins.sonar.tasks.web.SonarConfiguration;
import com.marvelution.bamboo.plugins.sonar.tasks.web.contextproviders.SonarConfigurationContextProvider;
import com.marvelution.bamboo.plugins.sonar.tasks.web.metrics.SonarMetricsManager;
import com.marvelution.bamboo.plugins.sonar.wsclient.services.Project;
import com.marvelution.bamboo.plugins.sonar.wsclient.services.ProjectQuery;
import com.marvelution.bamboo.plugins.sonar.wsclient.services.Version;
import com.marvelution.bamboo.plugins.sonar.wsclient.unmarshallers.ProjectUnmarshaller;

/**
 * {@link SonarConfigurationContextProvider} implementation specific for the Time Machine web panel
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarTimeMachineChartContextProvider extends SonarConfigurationContextProvider {

	private static final Logger LOGGER = Logger.getLogger(SonarTimeMachineChartContextProvider.class);

	private SonarMetricsManager sonarMetricsManager;
	private BambooAuthenticationContext authenticationContext;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getContextMap(Map<String, Object> context) {
		super.getContextMap(context);
		Chain chain = getChainPlan(context);
		User user = authenticationContext.getUser();
		List<String> metrics = null;
		if (chain != null && user != null) {
			LOGGER.debug("Checking if user " + user.getName() + " has custom metrics");
			metrics = sonarMetricsManager.getTimeMachineChartMetrics(chain, user);
		}
		if (metrics == null || metrics.isEmpty()) {
			metrics = sonarMetricsManager.getTimeMachineChartMetrics(chain);
		}
		context.put("metrics", metrics);
		SonarConfiguration sonarConfiguration = (SonarConfiguration) context.get("sonarConfiguration");
		if (sonarConfiguration.isAnalyzed()) {
			Sonar sonar = new Sonar(new HttpClient4Connector(sonarConfiguration.getSonarHost()));
			String json =
				sonar.getConnector().execute(new ProjectQuery(sonarConfiguration.getProjectKey()).setVersions(true));
			List<String> versionIds = Lists.newArrayList();
			if (json != null) {
				Project project = new ProjectUnmarshaller().toModel(json);
				for (Version version : project.getVersions()) {
					versionIds.add(Integer.toString(version.getSid()));
				}
			}
			context.put("versions", StringUtils.join(versionIds, ","));
		}
		return context;
	}

	/**
	 * Internal method to get the {@link Chain} object from the current plan
	 * 
	 * @param context the context containing the plan
	 * @return the {@link Chain} object, may be <code>null</code> if not found
	 */
	private Chain getChainPlan(Map<String, Object> context) {
		if (context.get("plan") instanceof Chain) {
			return (Chain) context.get("plan");
		} else if (context.get("plan") instanceof Buildable) {
			Job job = (Job) context.get("plan");
			return job.getParent();
		} else {
			return null;
		}
	}

	/**
	 * Setter for sonarMetricsManager
	 * 
	 * @param sonarMetricsManager the sonarMetricsManager to set
	 */
	public void setSonarMetricsManager(SonarMetricsManager sonarMetricsManager) {
		this.sonarMetricsManager = sonarMetricsManager;
	}

	/**
	 * Setter for authenticationContext
	 * 
	 * @param authenticationContext the authenticationContext to set
	 */
	public void setAuthenticationContext(BambooAuthenticationContext authenticationContext) {
		this.authenticationContext = authenticationContext;
	}

}
