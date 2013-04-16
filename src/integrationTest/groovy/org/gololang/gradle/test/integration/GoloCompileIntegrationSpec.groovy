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

class GoloCompileIntegrationSpec extends IntegrationSpec {
    void setup() {
        buildFile << """
            def GoloPlugin = project.class.classLoader.loadClass('org.gololang.gradle.GoloPlugin')

            apply plugin: GoloPlugin

            repositories {
                mavenCentral()
                maven { url 'https://oss.sonatype.org/content/repositories/snapshots' }
            }

            dependencies {
                golo 'org.golo-lang:golo:0-preview2-SNAPSHOT'
            }
        """
    }

    void 'compileGolo is up to date for an empty source set'() {
        when:
        runTasks('compileGolo')

        then:
        task('compileGolo').state.isUpToDate()
    }

    void 'compileGolo compiles files'() {
        given:
        file('src/main/golo/helloworld.golo') << """
            module hello.World

            function main = |args| {
                println("Hello world!")
            }
        """

        when:
        runTasks('compileGolo')

        then:
        fileExists('build/classes/main/hello/World.class')
    }
}
