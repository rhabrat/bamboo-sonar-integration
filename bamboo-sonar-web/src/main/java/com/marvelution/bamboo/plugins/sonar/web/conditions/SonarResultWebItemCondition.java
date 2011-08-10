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

import org.apache.commons.lang.StringUtils;

import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.chains.Chain;
import com.atlassian.bamboo.plan.IncorrectPlanTypeException;
import com.atlassian.bamboo.plan.PlanManager;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.marvelution.bamboo.plugins.sonar.tasks.predicates.IsSonarTaskPredicate;

/**
 * {@link Condition} implementation to specify when to display teh Sonar tab
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarResultWebItemCondition implements Condition {

	private PlanManager planManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Map<String, String> map) throws PluginParseException {
		// Not required by this Condition
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean shouldDisplay(Map<String, Object> context) {
		String key = StringUtils.defaultString((String) context.get("planKey"), (String) context.get("buildKey"));
		if (StringUtils.isNotBlank(key)) {
			for (Job job : getAllJobsByKey(key)) {
				if (Iterables.any(job.getBuildDefinition().getTaskDefinitions(), new IsSonarTaskPredicate())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Internal method to get a {@link List} of {@link Job} objects by a given Key
	 * 
	 * @param key the key to get the {@link Job} objects by, can be a plan key of a build key
	 * @return the {@link List} of {@link Job} objects
	 */
	private List<Job> getAllJobsByKey(String key) {
		List<Job> jobs = Lists.newArrayList();
		try {
			Chain plan = (Chain) planManager.getPlanByKey(key, Chain.class);
			jobs.addAll(plan.getAllJobs());
		} catch (IncorrectPlanTypeException e) {
			// Oke it was a build key and not a plan key
			jobs.add((Job) planManager.getPlanByKey(key, Job.class));
		}
		return jobs;
	}

	/**
	 * Setter for planManager
	 * 
	 * @param planManager the planManager to set
	 */
	public void setPlanManager(PlanManager planManager) {
		this.planManager = planManager;
	}

}
