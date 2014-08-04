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

import org.gradle.tooling.BuildException

/**
 * @author Marcin Erdmann
 */
class GoloCompileIntegrationSpec extends GoloPluginIntegrationSpec {

	void setup() {
		configureGoloConfiguration()
    }

	private File writeBadFile() {
		file('src/main/golo/helloworld.golo') << """
            module hello.World

            function main = |args|
        """
	}

    void 'compileGolo is up to date for an empty source set'() {
        when:
		run(COMPILE_GOLO_TASK_NAME)

        then:
		upToDate(":$COMPILE_GOLO_TASK_NAME")
    }

    void 'compileGolo compiles files'() {
        given:
		writeGoodFile()

        when:
        run(COMPILE_GOLO_TASK_NAME)

        then:
        fileExists('build/classes/main/hello/World.class')
    }

	void 'compileGolo prints out messages on compilation failure'() {
		given:
		writeBadFile()

		when:
		run(COMPILE_GOLO_TASK_NAME)

		then:
		thrown(BuildException)

		and:
		standardErrorOutput.contains('Compilation failed; see the compiler error output for details.')
		standardErrorOutput.contains('In Golo module: helloworld')
		standardErrorOutput =~ /(?s)Was expecting one of:.*"\{".*"->"/
	}
}
