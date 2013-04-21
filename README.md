# Gradle GOLO plugin [![Build Status](https://drone.io/github.com/erdi/gradle-golo-plugin/status.png)](https://drone.io/github.com/erdi/gradle-golo-plugin/latest)

The plugin provides a task type for compiling [Golo](http://golo-lang.org/) code. It also applies [Java Plugin](http://www.gradle.org/docs/current/userguide/java_plugin.html) and [Application Plugin](http://www.gradle.org/docs/current/userguide/application_plugin.html) to allow for building, packaging and executing code written in Golo.

## Usage

To use the plugin apply it to your project in your build script:

	apply plugin: 'golo'

You also need to add the plugin JAR to the classpath. The easiest way is to pull the artifact from [Sonatype OSS Snapshot Repository](https://oss.sonatype.org/content/repositories/snapshots/org/golo-lang/gradle-golo-plugin/):

	buildscript {
		repositories {
			mavenCentral()
			maven { url = 'https://oss.sonatype.org/content/repositories/snapshots/' }
		}

		dependencies {
			classpath 'org.golo-lang:gradle-golo-plugin:0.1-SNAPSHOT'
		}
	}

Finally you have to specify which version of Golo you wish to use to compile your project. You can do it using the `golo` configuration:

	repositories {
        mavenCentral()
    }

    dependencies {
        golo 'org.golo-lang:golo:0-preview2'
    }

## Project layout

The plugin adds a Golo source set directory to all source sets defined in the project. If you wish to add any Golo code to the main source you should put it in `src/main/golo` subdirectory of your project. If you wish to modify the location of a Golo directory for a source set you can do so by using project's `sourceSets` configuration block:

	sourceSets {
		main {
			golo {
				srcDir 'src/golo'
			}
		}
	}

## Tasks

The plugin adds `GoloCompile` task type. An instance of this type is created and configured per source set to compile Golo code from that source set.

## Extension properties

The plugin adds a `golo` extension to the project. Currently it has only one property, `mainModule`, than can be used to configure the main module of the application:

	golo {
		mainModule = 'hello.World'
	}

## Integration with [Java](http://www.gradle.org/docs/current/userguide/java_plugin.html) and [Application](http://www.gradle.org/docs/current/userguide/application_plugin.html) plugins

This plugin integrates with and applies Java and Application plugins to the project. Thanks to that you can seamlessly reuse all tasks from those plugins. To name just the most interesting ones:

* `build` - comming from Java Plugin, depending on `check` and `assemble`, allows to perform a full build of the project
* `run` - comming from Application Plugin, starts the application by running the main module specified in `golo.mainModule` extension property
* `distZip` and `distTar` - comming from Application plugin, allow to create a full distribution archive including runtime libraries and OS specific scripts which run the main module specified in `golo.mainModule` extension property

## Example

An example project using the plugin can be found [here](https://github.com/erdi/gradle-golo-plugin-example).