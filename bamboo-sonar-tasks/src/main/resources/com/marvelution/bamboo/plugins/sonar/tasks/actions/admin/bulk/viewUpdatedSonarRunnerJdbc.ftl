<!--
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
 -->

[#-- @ftlvariable name="action" type="com.atlassian.bamboo.ww2.actions.admin.bulk.BulkPlanAction" --]
[#-- @ftlvariable name="" type="com.atlassian.bamboo.ww2.actions.admin.bulk.BulkPlanAction" --]

[#if !bulkAction.getNewServerConfigured(params) ]
	[#if bulkAction.getNewUrl(params)?has_content]
		[@ww.label labelKey='sonar.jdbc.url' name='bulkAction.getNewUrl(params)' /]
	[#else]
		[@ww.label labelKey='sonar.jdbc.url' value='<i>[none specified]</i>' escape='false' /]
	[/#if]
	[#if bulkAction.getNewUsername(params)?has_content]
		[@ww.label labelKey='sonar.jdbc.username' name='bulkAction.getNewUsername(params)' /]
	[#else]
		[@ww.label labelKey='sonar.jdbc.username' value='<i>[none specified]</i>' escape='false' /]
	[/#if]
	[#if bulkAction.getNewPassword(params)?has_content]
		[@ww.label labelKey='sonar.jdbc.password' name='bulkAction.getNewPassword(params)' /]
	[#else]
		[@ww.label labelKey='sonar.jdbc.password' value='<i>[none specified]</i>' escape='false' /]
	[/#if]
	[#if bulkAction.getNewDriver(params)?has_content]
		[@ww.label labelKey='sonar.jdbc.driver' name='bulkAction.getNewDriver(params)' /]
	[#else]
		[@ww.label labelKey='sonar.jdbc.driver' value='<i>[none specified]</i>' escape='false' /]
	[/#if]
[#else]
	[@ww.label labelKey='sonar.runner.server.setup' value='<i>Yes</i>' escape="false" /]
[/#if]
