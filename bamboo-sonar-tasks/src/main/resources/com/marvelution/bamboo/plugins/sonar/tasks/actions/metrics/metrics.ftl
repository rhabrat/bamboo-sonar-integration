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

[#-- @ftlvariable name="plan" type="com.atlassian.bamboo.build.Build" --]

[#macro showMetricsEditorForBuild plan ]
	<dl style="clear: both;">
		<dt style="float: left; padding-left: 30px; padding-right: 10px">[@ww.text name='sonar.metrics.title' /]</dt>
		<dd>
			[@showMetricsWithNone plan /]
			[@showEditMetric plan /]
		</dd>
	</dl>
[/#macro]

[#macro showEditMetric plan showIcon=true]
	[#if user??]
		[@ww.url id="editMetricUrl" action='editMetrics' namespace='/sonar/ajax']
			[@ww.param name='buildKey']${plan.key}[/@ww.param]
		[/@ww.url]
		<a href="${editMetricUrl}" class="labels-edit" title="[@ww.text name='sonar.metrics.buttons.edit' /]">
			[#if showIcon]
				[@ui.icon type="edit" textKey="sonar.metrics.buttons.edit" showTitle=false /]
			[/#if]
		</a>
		[@ww.text id='metricsEditShortcut' name='sonar.metrics.edit.shortcut'][@ww.param]{shortcut}[/@ww.param][/@ww.text]
		<script type="text/javascript">
			BAMBOO.LABELS.init({
				labelsDialog: {
					header: "[@ww.text name='sonar.metrics.title' /]",
					shortcutKey: "M"
				},
				templates: {
					shortcutKeyTemplate: "${metricsEditShortcut?js_string}"
				}
			});
		</script>
	[/#if]
[/#macro]

[#macro showMetrics metrics plan='' ]
<ul class="label-list">
	[#list metrics as metric]
		<li id="label-${metric}">
			<a class="label">${metric?html}</a>
			[#if plan?has_content]
				<span class="remove-label-caption">${metric?html}</span>
				[@ww.url id="deleteMetricUrl" action='deleteMetric' namespace='/sonar/ajax']
					[@ww.param name='buildKey']${plan.key}[/@ww.param]
					[@ww.param name='selectedMetric']${metric?html}[/@ww.param]
				[/@ww.url]
				<a href="${deleteMetricUrl}" class="remove-label">[@ww.text name="sonar.metrics.buttons.delete" /]</a>
			[/#if]
		</li>
	[/#list]
</ul>
[/#macro]

[#macro showMetricsWithNone plan ]
	[#if metrics?has_content]
		[@showMetrics metrics plan /]
	[#else]
		<span class="label-none">
			[#if resultsSummary?has_content]
				[@ww.text name='sonar.metrics.none' /]
			[/#if]
		</span>
	[/#if]
[/#macro]
