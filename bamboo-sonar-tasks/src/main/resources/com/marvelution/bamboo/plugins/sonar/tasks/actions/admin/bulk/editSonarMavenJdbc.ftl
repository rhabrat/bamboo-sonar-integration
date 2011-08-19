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

[@ui.bambooSection titleKey='sonar.jdbc.configuration']
	[@ww.radio labelKey='sonar.jdbc.configuration.option' name='sonarJdbcOption'
               listKey='key' listValue='value' toggle='true'
               list=bulkAction.getJdbcOptions() ]
    [/@ww.radio]
    [@ui.bambooSection dependsOn='sonarJdbcOption' showOn='sonarJdbcUseProfile']
        [@ww.textfield labelKey='sonar.jdbc.profile' name='sonarJdbcProfile' required='true' cssClass="long-field" /]
    [/@ui.bambooSection]
    [@ui.bambooSection dependsOn='sonarJdbcOption' showOn='sonarJdbcUseForm']
        [@ww.textfield labelKey='sonar.jdbc.url' name='sonarJdbcUrl' cssClass="long-field" /]
        [@ww.textfield labelKey='sonar.jdbc.username' name='sonarJdbcUsername' cssClass="long-field" /]
        [@ww.password labelKey='sonar.jdbc.password' name='sonarJdbcPassword' cssClass="long-field" /]
        [@ww.textfield labelKey='sonar.jdbc.driver' name='sonarJdbcDriver' cssClass="long-field" /]
    [/@ui.bambooSection]
[/@ui.bambooSection]
