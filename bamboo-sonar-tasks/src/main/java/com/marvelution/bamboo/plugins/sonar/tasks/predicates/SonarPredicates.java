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

package com.marvelution.bamboo.plugins.sonar.tasks.predicates;

import org.jetbrains.annotations.Nullable;

import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.task.TaskIdentifier;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.marvelution.bamboo.plugins.sonar.tasks.utils.PluginHelper;


/**
 * Sonar related {@link Predicate} helper
 * 
 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
 */
public class SonarPredicates {

	/**
	 * Get the Is Sonar Task {@link Predicate}
	 * 
	 * @return the {@link Predicate}
	 */
	public static IsSonarTaskPredicate<TaskDefinition> isSonarTask() {
		return new IsSonarTaskPredicate<TaskDefinition>();
	}

	/**
	 * Get the Is Sonar Runner task {@link Predicate}
	 * 
	 * @return the {@link Predicate}
	 */
	public static IsSonarTaskTypePredicate<TaskDefinition> isSonarRunnerTask() {
		return new IsSonarTaskTypePredicate<TaskDefinition>("task.builder.sonar");
	}

	/**
	 * Get the is Sonar Maven 2 task {@link Predicate}
	 * 
	 * @return the {@link Predicate}
	 */
	public static IsSonarTaskTypePredicate<TaskDefinition> isSonarMaven2Task() {
		return new IsSonarTaskTypePredicate<TaskDefinition>("task.builder.sonar2");
	}

	/**
	 * Get the is Sonar Maven 3 task {@link Predicate}
	 * 
	 * @return the {@link Predicate}
	 */
	public static IsSonarTaskTypePredicate<TaskDefinition> isSonarMaven3Task() {
		return new IsSonarTaskTypePredicate<TaskDefinition>("task.builder.sonar3");
	}

	/**
	 * Get the is Sonar Maven task {@link Predicate}
	 * 
	 * @return the {@link Predicate}
	 */
	public static Predicate<TaskDefinition> isSonarMavenTask() {
		return Predicates.or(isSonarMaven2Task(), isSonarMaven3Task());
	}

	/**
	 * Specifci Sonar Task {@link Predicate} implementation
	 * 
	 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
	 *
	 * @param <TASKDEF>
	 */
	private static class IsSonarTaskTypePredicate<TASKDEF extends TaskIdentifier> implements Predicate<TASKDEF> {

		private final String taskTypeKey;

		/**
		 * Default Constructor
		 * 
		 * @param taskTypeKey the Sonar TaskType key
		 */
		protected IsSonarTaskTypePredicate(String taskTypeKey) {
			this.taskTypeKey = taskTypeKey;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean apply(@Nullable TaskIdentifier taskIdentifier) {
			return ((TaskIdentifier) Preconditions.checkNotNull(taskIdentifier)).getPluginKey().equals(
				PluginHelper.getPluginKey() + ":" + taskTypeKey);
		}

	}

	/**
	 * Generic "Is Sonar" {@link Predicate} implementation
	 * 
	 * @author <a href="mailto:markrekveld@marvelution.com">Mark Rekveld</a>
	 *
	 * @param <TASKDEF>
	 */
	private static class IsSonarTaskPredicate<TASKDEF extends TaskIdentifier> implements Predicate<TASKDEF> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean apply(@Nullable TASKDEF taskIdentifier) {
			return ((TaskIdentifier) Preconditions.checkNotNull(taskIdentifier)).getPluginKey().startsWith(
				PluginHelper.getPluginKey() + ":task.builder.sonar");
		}

	}

}
