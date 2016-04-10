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
package org.gololang.gradle.test

import org.gololang.gradle.GoloSourceSet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.api.internal.file.SourceDirectorySetFactory
import org.gradle.api.internal.file.collections.DirectoryFileTree
import org.gradle.api.internal.file.collections.DirectoryFileTreeFactory
import org.gradle.api.tasks.util.PatternSet
import org.gradle.internal.Factory
import org.gradle.internal.nativeintegration.services.NativeServices
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 * @author Marcin Erdmann
 */
class GoloSourceSetSpec extends Specification {

	@Rule
	TemporaryFolder nativeServicesHome

	def fileResolver = Stub(FileResolver) {
		resolve(_) >> { it as File }
		getPatternSetFactory() >> {
			new Factory<PatternSet>() {
				PatternSet create() {
					new PatternSet()
				}
			}
		}
	}

	def directoryFileTreeFactory = Stub(DirectoryFileTreeFactory) {
		create(_, _) >> { dir, pattern -> new DirectoryFileTree(dir, pattern) }
	}

	def sourceDirectorySetFactory = Stub(SourceDirectorySetFactory) {
		create(_) >> { String name -> new DefaultSourceDirectorySet(name, fileResolver, directoryFileTreeFactory) }
	}

	def sourceSet = new GoloSourceSet('<display-name>', sourceDirectorySetFactory)

	void setup() {
		NativeServices.initialize(nativeServicesHome.root)
	}

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
		given:
		def mainGoloSourceDir = 'src/golo'

		when:
		sourceSet.golo { srcDir mainGoloSourceDir }

		then:
		sourceSet.golo.srcDirs == [new File(mainGoloSourceDir).canonicalFile] as Set
	}
}
