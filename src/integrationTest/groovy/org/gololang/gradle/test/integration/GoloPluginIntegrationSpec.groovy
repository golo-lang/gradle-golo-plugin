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

import org.gololang.gradle.test.integration.framework.IntegrationSpec

abstract class GoloPluginIntegrationSpec extends IntegrationSpec {

	protected static final String COMPILE_GOLO_TASK_NAME = 'compileGolo'

	void setup() {
		buildFile << """
            def GoloPlugin = project.class.classLoader.loadClass('org.gololang.gradle.GoloPlugin')

            apply plugin: GoloPlugin

            repositories {
                mavenCentral()
            }
        """
	}

	protected File writeGoodFile() {
		file('src/main/golo/helloworld.golo') << """
            module hello.World

            function main = |args| {
                println("Hello world!")
            }
        """
	}
}
