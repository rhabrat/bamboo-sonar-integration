Sonar Configurations<br />
<br />
[#list sonarConfigurations as config]
Host: ${config.host}<br />
[#if config.isSecured()]
Username: ${config.username}<br />
Password: ${config.password}<br />
[/#if]
Project Key: ${config.projectKey}<br />
Project Name: ${config.projectName}<br />
[/#list]
