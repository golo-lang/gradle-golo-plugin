package org.gololang.gradle.test.integration

import org.gololang.gradle.GoloSourceSet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import spock.lang.Specification

import static org.junit.Assert.assertThat

class GoloSourceSetSpec extends Specification {
    def sourceSet = new GoloSourceSet('<display-name>', [resolve: {it as File}] as FileResolver)

    void 'default values'() {
        expect:
        with sourceSet, {
            golo in DefaultSourceDirectorySet
            !golo.iterator().hasNext()
            golo.name == '<display-name> Golo source'
            golo.filter.includes == ['**/*.golo'] as Set
            golo.filter.excludes == [] as Set
        }
    }

    void 'can configure source set'() {
        when:
        sourceSet.golo { srcDir 'src/golo' }

        then:
        sourceSet.golo.srcDirs == [new File('src/golo').canonicalFile] as Set
    }
}
