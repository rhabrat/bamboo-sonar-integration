[#--
 ~ Licensed to Marvelution under one or more contributor license
 ~ agreements.  See the NOTICE file distributed with this work
 ~ for additional information regarding copyright ownership.
 ~ Marvelution licenses this file to you under the Apache License,
 ~ Version 2.0 (the "License"); you may not use this file except
 ~ in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~  http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing,
 ~ software distributed under the License is distributed on an
 ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 ~ KIND, either express or implied. See the License for the
 ~ specific language governing permissions and limitations
 ~ under the License.
 --]

[#-- @ftlvariable name="action" type="com.marvelution.bamboo.plugins.sonar.tasks.actions.admin.ViewSonarBuildTaskConfiguration" --]
[#-- @ftlvariable name="" type="com.marvelution.bamboo.plugins.sonar.tasks.actions.admin.ViewSonarBuildTaskConfiguration" --]

[#list action.getTaskDefinitions() as taskDefinition]
	[#if !taskDefinition.getServerConfigured() ]
		[#if taskDefinition.getJdbcUrl()?has_content]
			[@ww.label labelKey='sonar.jdbc.url' value=taskDefinition.getJdbcUrl() /]
		[#else]
			[@ww.label labelKey='sonar.jdbc.url' value='<i>[none specified]</i>' escape='false' /]
		[/#if]
		[#if taskDefinition.getJdbcUsername()?has_content]
			[@ww.label labelKey='sonar.jdbc.username' value=taskDefinition.getJdbcUsername() /]
		[#else]
			[@ww.label labelKey='sonar.jdbc.username' value='<i>[none specified]</i>' escape='false' /]
		[/#if]
		[#if taskDefinition.getJdbcPassword()?has_content]
			[@ww.label labelKey='sonar.jdbc.password' value=taskDefinition.getJdbcPassword() /]
		[#else]
			[@ww.label labelKey='sonar.jdbc.password' value='<i>[none specified]</i>' escape='false' /]
		[/#if]
		[#if taskDefinition.getJdbcDriver()?has_content]
			[@ww.label labelKey='sonar.jdbc.driver' value=taskDefinition.getJdbcDriver() /]
		[#else]
			[@ww.label labelKey='sonar.jdbc.driver' value='<i>[none specified]</i>' escape='false' /]
		[/#if]
	[#else]
		[@ww.label labelKey='sonar.runner.server.setup' value='<i>Yes</i>' escape="false" /]
	[/#if]
[/#list]
