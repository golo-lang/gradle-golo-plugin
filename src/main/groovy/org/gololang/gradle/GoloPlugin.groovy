package org.gololang.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.internal.file.FileResolver
import org.gradle.api.internal.plugins.DslObject
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.JavaExec

import javax.inject.Inject

import static org.gololang.gradle.GoloCompile.GOLO_CLASSPATH_FIELD

class GoloPlugin implements Plugin<Project> {

    public static final String GOLO_PLUGIN_NAME = "golo"
    public static final String GOLO_GROUP_NAME = GOLO_PLUGIN_NAME
    public static final String GOLO_CONFIGURATION_NAME = GOLO_PLUGIN_NAME

    public static final String TASK_RUN_NAME = "run"

    Project project
    FileResolver fileResolver
    Configuration goloConfiguration
    GoloPluginExtension pluginExtension

    @Inject
    public GoloPlugin(FileResolver fileResolver) {
        this.fileResolver = fileResolver
    }

    @Override
    void apply(Project project) {
        this.project = project
        project.plugins.apply(JavaPlugin)

        configureSourceSetDefaults(project.plugins.getPlugin(JavaBasePlugin))
        configureGoloConfigurationAndClasspath()

        addGoloPluginExtension()
        addRunTask()
    }

    private void configureGoloConfigurationAndClasspath() {
        goloConfiguration = project.configurations.create(GOLO_CONFIGURATION_NAME)
                .setVisible(false)
                .setDescription("The Golo libraries to be used for this Golo project.")

        project.tasks.withType(GoloCompile) { GoloCompile goloCompile ->
            goloCompile.conventionMapping.map(GOLO_CLASSPATH_FIELD) { goloConfiguration }
        }
    }

    void configureSourceSetDefaults(JavaBasePlugin javaBasePlugin) {
        project.convention.getPlugin(JavaPluginConvention).sourceSets.all { sourceSet ->
            def goloSourceSet = new GoloSourceSet(sourceSet.displayName, fileResolver)
            new DslObject(sourceSet).convention.plugins.put('golo', goloSourceSet)

            goloSourceSet.golo.srcDir("src/${sourceSet.name}/golo")

            def compileTaskName = sourceSet.getCompileTaskName('golo')

            def goloCompile = project.tasks.add(compileTaskName, GoloCompile)
            javaBasePlugin.configureForSourceSet(sourceSet, goloCompile)
            goloCompile.dependsOn(sourceSet.compileJavaTaskName)
            goloCompile.setDescription("Compiles the ${sourceSet.name} Groovy source.")
            goloCompile.setSource(goloSourceSet.golo)

            project.tasks.getByName(sourceSet.classesTaskName).dependsOn(compileTaskName)
        }
    }

    private void addGoloPluginExtension() {
        pluginExtension = project.extensions.create(GOLO_PLUGIN_NAME, GoloPluginExtension)
    }

    private void addRunTask() {
        def run = project.tasks.add(TASK_RUN_NAME, JavaExec)
        run.description = "Runs this project as a Golo application"
        run.group = GOLO_GROUP_NAME
        run.classpath = project.sourceSets.main.runtimeClasspath + goloConfiguration
        run.main = 'fr.insalyon.citi.golo.cli.MainGolo'
        run.doFirst { it.args pluginExtension.mainModule }
    }
}
