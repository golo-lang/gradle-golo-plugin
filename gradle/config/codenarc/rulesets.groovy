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
ruleset {
	ruleset('rulesets/basic.xml')
	ruleset('rulesets/braces.xml')
	ruleset('rulesets/concurrency.xml')
	ruleset('rulesets/convention.xml')
	ruleset('rulesets/design.xml') {
		AbstractClassWithoutAbstractMethod {
			doNotApplyToClassNames = 'GoloPluginIntegrationSpec, IntegrationSpec'
		}
	}
	ruleset('rulesets/dry.xml') {
		DuplicateStringLiteral {
			doNotApplyToClassNames = 'org.gololang.gradle.test.integration.framework.IntegrationSpec,GoloCompileIntegrationSpec'
		}
	}
	ruleset('rulesets/exceptions.xml') {
		CatchThrowable {
			doNotApplyToClassNames = 'org.gololang.gradle.GoloCompile'
		}
	}
	ruleset('rulesets/formatting.xml') {
		SpaceAfterClosingBrace {
			doNotApplyToClassNames = 'org.gololang.gradle.test.integration.framework.IntegrationSpec$1'
			checkClosureMapEntryValue = false
		}
		LineLength {
			length = 125
		}
		ClassJavadoc {
			enabled = false
		}
		SpaceAroundMapEntryColon {
			characterAfterColonRegex = /\s/
		}
	}
	ruleset('rulesets/generic.xml') {
		RequiredString {
			string = 'Copyright'
			violationMessage = 'Copyright header not found'
		}
	}
	ruleset('rulesets/grails.xml')
	ruleset('rulesets/groovyism.xml')
	ruleset('rulesets/imports.xml') {
		MisorderedStaticImports {
			comesBefore = false
		}
	}
	ruleset('rulesets/jdbc.xml')
	ruleset('rulesets/junit.xml')
	ruleset('rulesets/logging.xml') {
		SystemErrPrint {
			doNotApplyToClassNames = 'org.gololang.gradle.GoloCompile'
		}
	}
	ruleset('rulesets/naming.xml') {
		MethodName {
			regex = /[a-z]["#\w\s]*/
		}
		ConfusingMethodName {
			doNotApplyToClassNames = 'org.gololang.gradle.GoloSourceSet'
		}
		FactoryMethodName {
			enabled = false
		}
	}
	ruleset('rulesets/security.xml') {
		JavaIoPackageAccess {
			enabled = false
		}
	}
	ruleset('rulesets/serialization.xml')
	ruleset('rulesets/size.xml') {
		CrapMetric {
			enabled = false
		}
	}
	ruleset('rulesets/unnecessary.xml') {
		UnnecessaryGetter {
			doNotApplyToClassNames = 'org.gololang.gradle.GoloCompile,org.gololang.gradle.test.GoloSourceSetSpec'
		}
	}
	ruleset('rulesets/unused.xml') {
		UnusedMethodParameter {
			doNotApplyToClassNames = 'org.gololang.gradle.GoloCompile'
		}
	}
}