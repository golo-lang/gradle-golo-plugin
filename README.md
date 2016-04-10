# Gradle GOLO plugin [![Build Status](https://drone.io/github.com/golo-lang/gradle-golo-plugin/status.png)](https://drone.io/github.com/golo-lang/gradle-golo-plugin/latest)

The plugin provides a task type for compiling [Golo](http://golo-lang.org/) code. It also applies [Java Plugin](http://www.gradle.org/docs/current/userguide/java_plugin.html) and [Application Plugin](http://www.gradle.org/docs/current/userguide/application_plugin.html) to allow for building, packaging and executing code written in Golo.

## Usage

To use the plugin you will first need to apply it to your project. Please refer to [the plugin's page in Gradle Plugin Portal](http://plugins.gradle.org/plugin/org.golo-lang.golo) for instructions on how to do it in the Gradle version you're using.

You will also have to specify which version of Golo you wish to use to compile your project. You can do it using the `golo` configuration:

	repositories {
		jcenter()
	}

	dependencies {
		golo 'org.eclipse.golo:golo:3.1.0'
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
