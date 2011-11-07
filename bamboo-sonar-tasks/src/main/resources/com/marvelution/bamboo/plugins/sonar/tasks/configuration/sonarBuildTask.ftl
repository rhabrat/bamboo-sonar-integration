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

[#macro showSonarHostEditor ]
	[@ui.bambooSection titleKey='sonar.host.configuration']
		[@ww.textfield labelKey='sonar.host.url' name='sonarHostUrl' required='true' cssClass="long-field" /]
		[@ww.textfield labelKey='sonar.host.username' name='sonarHostUsername' cssClass="long-field" /]
		[@ww.textfield labelKey='sonar.host.password' name='sonarHostPassword' cssClass="long-field" /]
	[/@ui.bambooSection]
[/#macro]

[#macro showSonarJDBCEditor ]
	[@ww.textfield labelKey='sonar.jdbc.url' name='sonarJdbcUrl' cssClass="long-field" /]
	[@ww.textfield labelKey='sonar.jdbc.username' name='sonarJdbcUsername' cssClass="long-field" /]
	[@ww.textfield labelKey='sonar.jdbc.password' name='sonarJdbcPassword' cssClass="long-field" /]
	[@ww.textfield labelKey='sonar.jdbc.driver' name='sonarJdbcDriver' cssClass="long-field" /]
[/#macro]
