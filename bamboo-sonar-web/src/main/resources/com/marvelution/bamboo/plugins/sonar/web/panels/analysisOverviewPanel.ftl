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

<h2>[@ww.text name="sonar.host.configuration" /]</h2>
[#assign host]<a href="${sonarConfiguration.host}">${sonarConfiguration.host}</a>[/#assign]
[@ww.label labelKey='sonar.host.url' value='${host}' escape='false' /]
[#if sonarConfiguration.isSecured()]
	[@ww.label labelKey='sonar.host.username' value='${sonarConfiguration.username}' /]
[/#if]
<h2>[@ww.text name="sonar.project.configuration" /]</h2>
[#if sonarConfiguration.isAnalyzed()]
	[@ww.label labelKey='sonar.project.name' value='${sonarConfiguration.projectName}' /]
	[@ww.label labelKey='sonar.project.key' value='${sonarConfiguration.projectKey}' /]
[#else]
	[@ww.text name='sonar.web.panel.project.not.analyzed' /]
[/#if]
