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
[#import "/com/marvelution/bamboo/plugins/sonar/tasks/configuration/sonarBuildTask.ftl" as sbt /]

[#assign addJdkLink][@ui.displayAddJdkInline /][/#assign]
[@ww.select cssClass="jdkSelectWidget"
            labelKey='builder.common.jdk' name='buildJdk'
            list=uiConfigBean.jdkLabels required='true'
            extraUtility=addJdkLink /]

[@ww.textfield labelKey='builder.maven2.projectFile' name='projectFile' cssClass="long-field" /]
[@ww.textfield labelKey='builder.common.env' name='environmentVariables' cssClass="long-field" /]
[@ww.textfield labelKey='builder.common.sub' name='workingSubDirectory' helpUri='working-directory.ftl' cssClass="long-field" /]
[@ww.checkbox labelKey='sonar.plugin.preinstalled' name='sonarPluginPreInstalled' /]

[@sbt.showSonarHostEditor /]
[@ui.bambooSection titleKey='sonar.jdbc.configuration']
	[@ww.radio labelKey='sonar.jdbc.configuration.option' name='sonarJdbcOption'
               listKey='key' listValue='value' toggle='true'
               list=sonarJdbcOptions ]
    [/@ww.radio]
    [@ui.bambooSection dependsOn='sonarJdbcOption' showOn='sonarJdbcUseProfile']
        [@ww.textfield labelKey='sonar.jdbc.profile' name='sonarJdbcProfile' required='true' cssClass="long-field" /]
    [/@ui.bambooSection]
    [@ui.bambooSection dependsOn='sonarJdbcOption' showOn='sonarJdbcUseForm']
	[@sbt.showSonarJDBCEditor /]
    [/@ui.bambooSection]
[/@ui.bambooSection]

[#include "commonSonarBuildTaskEdit.ftl"]
