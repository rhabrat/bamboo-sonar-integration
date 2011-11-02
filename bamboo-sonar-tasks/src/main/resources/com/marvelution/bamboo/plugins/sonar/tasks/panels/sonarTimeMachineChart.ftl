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

[#if sonarConfiguration.isAnalyzed()]
[#import "/com/marvelution/bamboo/plugins/sonar/tasks/actions/metrics/metrics.ftl" as met /]
<h2>Time Machine Chart</h2>
[@met.showMetricsEditorForBuild plan /]
<img id="timeMachineChart" src="" style="display: none; padding: 10px;" />
<script type="text/javascript">
	var BASE_IMG_SRC = "${sonarConfiguration.host}/charts/trends/${sonarConfiguration.projectKey}?locale=en-US&sids=${versions}&metrics=";
	function refreshTimeMachineChart() {
		var timeMachineChart = AJS.$("#timeMachineChart");
		var metrics = new Array();
		AJS.$(".label-list .label").each(function() {
			if (AJS.$.inArray(this.text, metrics) == -1) {
				metrics.push(this.text);
			}
		});
		var width = timeMachineChart.parent().width() - 50;
		var height = Math.round(width / 2.8);
		timeMachineChart.attr({src: BASE_IMG_SRC + metrics.join() + "&ts=" + new Date().getTime() + "&w=" + width + "&h=" + height});
		timeMachineChart.css({display: "block"});
	}
	AJS.$(document).ready(function() {
		refreshTimeMachineChart();
		AJS.$("#timeMachineChart").bind("ajaxSuccess", function(e, xhr, settings) {
			if (settings.url.indexOf("sonar/ajax") != -1) {
				refreshTimeMachineChart();
			}
		});
	});
</script>
[#else]
	[@ww.text name='sonar.web.panel.project.not.analyzed' /]
[/#if]
