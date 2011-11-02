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

[#assign artifactTools]
    <ul class="toolbar-group">
        <li class="toolbar-item">
            <a id="viewGadgets" class="toolbar-trigger" title="[@ww.text name='sonar.view.gadget.list' /]">
            	[@ww.text name='sonar.view.gadget.list' /]
            </a>
        </li>
    </ul>
[/#assign]
[@ui.bambooPanel titleKey='sonar.panel.header' cssClass='form-view-header' auiToolbar=artifactTools headerWeight='h1' /]
<div id="main-panel">
	<div class="column" id="primary">
		[@ui.renderWebPanels 'sonar.web.panel' /]
	</div>
	<div class="column" id="secondary">
		[@ui.renderWebPanels 'sonar.web.panel.gadgets' /]
	</div>
</div>

<div class="hidden">
	<ol id="gadgetList" class="gadgetList">
	[#list gadgetIds as gadgetId]
		<li>
			<h3>[@ww.text name='sonar.gadget.${gadgetId}.title' /]</h3>
			<img src="${baseUrl}/download/resources/${pluginKey}/images/gadgets/sonar-${gadgetId}-thumb.png" width="120" height="60" />
			<p>[@ww.text name='sonar.gadget.${gadgetId}.description' /]</p>
			<a href="${baseUrl}/rest/gadgets/1.0/g/com.marvelution.bamboo.plugins.sonar.tasks/gadgets/sonar-${gadgetId}-gadget.xml" target="_parent">[@ww.text name='sonar.gadget.url' /]</a>
		</li>
	[/#list]
	</ol>
</div>

<script type="text/javascript">
var gadgetPopup;
AJS.$(document).ready(function() {
	gadgetPopup = new AJS.Dialog({width:650, height:500, id:"view-gadgets-dialog", closeOnOutsideClick: true});
	gadgetPopup.addHeader("[@ww.text name='sonar.gadgets.list.heading' /]");
	gadgetPopup.addPanel("Panel 1", "panel1");
	gadgetPopup.getCurrentPanel().body.append(AJS.$("#gadgetList"));
	gadgetPopup.addCancel("[@ww.text name='sonar.gadgets.list.close' /]", function(dialog) {
		dialog.hide();
	});
	gadgetPopup.gotoPage(0);
	gadgetPopup.gotoPanel(0);
	AJS.$(".gadgetList li").hover(function() {
		AJS.$(this).addClass('hover');
	}, function() {
		AJS.$(this).removeClass('hover');
	});
});
AJS.$("#viewGadgets").click(function(event) {
	gadgetPopup.show();
	event.preventDefault();
});
</script>