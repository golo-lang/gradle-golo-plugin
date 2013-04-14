package org.gololang.gradle.test.integration

import org.gololang.gradle.test.integration.framework.IntegrationSpec

class GoloPluginIntegrationSpec extends IntegrationSpec {
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
}
