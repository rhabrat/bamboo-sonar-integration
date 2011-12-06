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

[#-- @ftlvariable name="action" type="com.marvelution.bamboo.plugins.sonar.tasks.web.actions.chain.ViewChainSonarResult" --]
[#-- @ftlvariable name="" type="com.marvelution.bamboo.plugins.sonar.tasks.web.actions.ViewChainSonarResult" --]
[#-- @ftlvariable name="resultsSummary" type="com.atlassian.bamboo.chains.ChainResultsSummary" --]

<html>
<head>
    <title>[@ui.header pageKey='sonar.panel.title' object='${plan.name} ${chainResultNumber}' title=true /]</title>
    <meta name="tab" content="sonar" />
    <meta name="decorator" content="result">
    ${webResourceManager.requireResource("${pluginKey}:sonar-panel")}
</head>
<body>
	[#include '../common/commonViewSonarResult.ftl'/]
</body>
</html>
