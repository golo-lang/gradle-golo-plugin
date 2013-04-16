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
