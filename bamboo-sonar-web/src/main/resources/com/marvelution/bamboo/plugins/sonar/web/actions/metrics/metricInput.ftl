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

[#-- @ftlvariable name="action" type="com.marvelution.bamboo.plugins.sonar.web.actions.metrics.EditMetrics" --]
[#-- @ftlvariable name="" type="com.marvelution.bamboo.plugins.sonar.web.actions.metrics.EditMetrics" --]

[#assign submitBtn]<input type="submit" value="[@ww.text name='global.buttons.add' /]" />[/#assign]
[@ww.form id='labelForm' action="addMetrics" namespace="/sonar/ajax" cssClass="top-label"]
    [@ww.hidden name='buildKey' /]
    <fieldset class="group">
        <legend><span>[@ww.text name="sonar.metrics.edit.prompt" /]</span></legend>
        [@ww.textfield labelKey='metrics.title' name='metricInput' after=submitBtn labelClass='assistive' /]
    </fieldset>
[/@ww.form]
[#import "/com/marvelution/bamboo/plugins/sonar/web/actions/metrics/metrics.ftl" as met /]
[@met.showMetricsWithNone plan /]
