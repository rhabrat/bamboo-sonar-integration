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

package com.marvelution.bamboo.plugins.sonar.tasks.actions.admin;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.ww2.BambooActionSupport;
import com.atlassian.bamboo.ww2.aware.PlanAware;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.marvelution.bamboo.plugins.sonar.tasks.actions.admin.utils.SonarConfigurationHelper;
import com.marvelution.bamboo.plugins.sonar.tasks.predicates.SonarPredicates;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.SonarTaskUtils;

/**
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
@SuppressWarnings("unchecked")
public class ViewSonarBuildTaskConfiguration extends BambooActionSupport implements PlanAware {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ViewSonarBuildTaskConfiguration.class);

	private Plan plan;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String doDefault() throws Exception {
		return INPUT;
	}

	/**
	 * Get all the {@link TaskDefinition}s available via the {@link #getPlan()}
	 * 
	 * @return the {@link List} of {@link SonarConfigurationHelper} objects
	 */
	public List<SonarConfigurationHelper> getTaskDefinitions() {
		final List<SonarConfigurationHelper> tasks = Lists.newArrayList();
		for (Job job : SonarTaskUtils.getJobsWithSonarTasks(getPlan())) {
			LOGGER.debug("Checking job " + job.getBuildKey() + " for Sonar Tasks");
			TaskDefinition taskDefinition = Iterables.find(job.getBuildDefinition().getTaskDefinitions(),
				SonarPredicates.isSonarTask(), null);
			if (taskDefinition != null ){
				LOGGER.debug("Located valid Sonar Task with Id " + taskDefinition.getId());
				tasks.add(new SonarConfigurationHelper(job, taskDefinition));
			}
		}
		return Collections.unmodifiableList(tasks);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Plan getPlan() {
		return plan;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

}
