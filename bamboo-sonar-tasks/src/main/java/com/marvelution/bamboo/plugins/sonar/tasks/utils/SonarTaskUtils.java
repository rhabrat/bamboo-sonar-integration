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

package com.marvelution.bamboo.plugins.sonar.tasks.utils;

import java.util.List;

import org.apache.log4j.Logger;

import com.atlassian.bamboo.build.Buildable;
import com.atlassian.bamboo.build.Job;
import com.atlassian.bamboo.chains.Chain;
import com.atlassian.bamboo.plan.Plan;
import com.atlassian.bamboo.task.TaskDefinition;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.marvelution.bamboo.plugins.sonar.tasks.predicates.SonarPredicates;

/**
 * Sonar Tasks Utilities
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarTaskUtils {

	private static final Logger LOGGER = Logger.getLogger(SonarTaskUtils.class);

	/**
	 * Check if a given {@link Plan} has any Sonar Tasks configured
	 * 
	 * @param plan the {@link Plan} to check
	 * @return <code>true</code> if found, <code>false</code> otherwise
	 */
	public static boolean hasSonarTasks(Plan plan) {
		return hasSonarTasks(plan, SonarPredicates.isSonarTask());
	}

	/**
	 * Check if a given {@link Plan} has any Sonar Tasks configured
	 * 
	 * @param plan the {@link Plan} to check
	 * @param predicate the {@link Predicate} to use
	 * @return <code>true</code> if found, <code>false</code> otherwise
	 */
	public static boolean hasSonarTasks(Plan plan, Predicate<TaskDefinition> predicate) {
		if (plan instanceof Chain) {
			LOGGER.debug("The Plan is a Chain, get all the Jobs that have a Sonar Task");
			for (Job job : ((Chain) plan).getAllJobs()) {
				if (Iterables.any(job.getBuildDefinition().getTaskDefinitions(), predicate)) {
					return true;
				}
			}
		} else if (plan instanceof Buildable) {
			LOGGER.debug("The Plan is a Buildable, check if it has a Sonar Task");
			Job job = (Job) plan;
			if (Iterables.any(job.getBuildDefinition().getTaskDefinitions(), predicate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get all the {@link Job}s that have a Sonar Task using the {@link SonarPredicates#isSonarRunnerTask()} {@link Predicate}
	 * 
	 * @param plan the {@link Plan} to get the {@link Job}s from
	 * @return the {@link List} of {@link Job}s
	 */
	public static List<Job> getJobsWithSonarTasks(Plan plan) {
		return getJobsWithSonarTasks(plan, SonarPredicates.isSonarTask());
	}

	/**
	 * Get all the {@link Job}s that have a Sonar Task the specified {@link Predicate}
	 * 
	 * @param plan the {@link Plan} to get the {@link Job}s from
	 * @param predicate the {@link Predicate} to use
	 * @return the {@link List} of {@link Job}s
	 */
	public static List<Job> getJobsWithSonarTasks(Plan plan, Predicate<TaskDefinition> predicate) {
		List<Job> jobs = Lists.newArrayList();
		if (plan instanceof Chain) {
			for (Job job : ((Chain) plan).getAllJobs()) {
				if (Iterables.any(job.getBuildDefinition().getTaskDefinitions(), predicate)) {
					jobs.add(job);
				}
			}
		} else if (plan instanceof Buildable) {
			Job job = (Job) plan;
			if (Iterables.any(job.getBuildDefinition().getTaskDefinitions(), predicate)) {
				jobs.add(job);
			}
		}
		return jobs;
	}

}
