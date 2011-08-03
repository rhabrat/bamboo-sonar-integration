${project.name}
-------------------------------
${project.version}

What is it?
-------------------------------
${project.description}


BAMBOO - Quick Installation Guide
-------------------------------
1. Shutdown the Bamboo server instance, and all agents
2. Remove any existing Bamboo Sonar Integration plugins of previous versions
3. Install the new tasks plugin (bamboo-sonar-tasks-${project.version}.jar) in the [BAMBOO_INSTALL]/webapp/WEB-INF/lib directory
4. Install the new web plugin (bamboo-sonar-web-${project.version}.jar) in the [BAMBOO_HOME]/plugins directory
5. Start the Bamboo server instance, and all the agents


MORE?
-------------------------------
Detailed installation and usage instructions?

    http://docs.marvelution.com/display/BAMSON

Suggestions, bug reports or feature requests?

    ${project.issueManagement.url}

Support?

    Create a Support ticket at ${project.issueManagement.url}


Enjoy! :)
