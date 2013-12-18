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

import spock.lang.Unroll

import static org.gradle.api.plugins.ApplicationPlugin.TASK_RUN_NAME
import static org.gradle.api.plugins.ApplicationPlugin.TASK_START_SCRIPTS_NAME

/**
 * @author Marcin Erdmann
 */
class GoloConfigurationIntegrationSpec extends GoloPluginIntegrationSpec {
	void setup() {
		writeGoodFile()
	}

	void 'an informative message is presented if golo dependency is not specified'() {
		when:
		runTasksWithFailure(COMPILE_GOLO_TASK_NAME)

		then:
		standardErrorOutput.contains('You must assign a Golo library to the "golo" configuration.')
	}

	@Unroll
	void 'an informative message is presented if main module is not specified and "#task" is executed'() {
		given:
		configureGoloConfiguration()

		when:
		runTasksWithFailure(task)

		then:
		standardErrorOutput.contains('You must specify the mainModule using golo extension.')

		where:
		task << [TASK_RUN_NAME, TASK_START_SCRIPTS_NAME]
	}
}
