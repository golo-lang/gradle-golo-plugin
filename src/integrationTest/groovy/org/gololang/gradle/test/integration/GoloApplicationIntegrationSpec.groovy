/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */





package org.gololang.gradle.test.integration

import static org.gradle.api.plugins.ApplicationPlugin.TASK_RUN_NAME

/**
 * @author Marcin Erdmann
 */
class GoloApplicationIntegrationSpec extends GoloPluginIntegrationSpec {
	void setup() {
		configureGoloConfiguration()
		writeMainModuleFile()
		buildFile << """
			golo.mainModule = 'hello.World'
		"""
	}

	private writeMainModuleFile() {
		file('src/main/golo/helloworld.golo') << """
            module hello.World

            import java.io.File

            function main = |args| {
                File("createdByGolo"): mkdir()
            }
        """
	}

	void 'run executes main module'() {
		when:
		runTasksSuccessfully(TASK_RUN_NAME)

		then:
		fileExists('createdByGolo')
	}

	void 'running packaged app executes main module'() {
		given:
		buildFile << """
			applicationName = 'installScriptTest'

			installApp.destinationDir = file('installed')

			task runInstalledApp(type: Exec) {
				dependsOn installApp
				workingDir file('installed/bin')
				commandLine './installScriptTest'
			}
		"""

		when:
		runTasksSuccessfully('runInstalledApp')

		then:
		fileExists('installed/bin/createdByGolo')
	}
}
