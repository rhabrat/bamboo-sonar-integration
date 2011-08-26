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
