package org.gololang.gradle.test.integration.framework

import org.gradle.BuildResult
import org.gradle.GradleLauncher
import org.gradle.StartParameter
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.tasks.TaskState
import org.gradle.initialization.DefaultGradleLauncher
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.util.mop.Use

@Use(UpToDateCategory)
abstract class IntegrationSpec extends Specification {
    @Rule final TemporaryFolder dir = new TemporaryFolder()

    protected List<ExecutedTask> executedTasks = []

    protected GradleLauncher launcher(String... args) {
        StartParameter startParameter = GradleLauncher.createStartParameter(args)
        startParameter.setProjectDir(dir.root)
        DefaultGradleLauncher launcher = GradleLauncher.newInstance(startParameter)
        launcher.gradle.scriptClassLoader.addParent(getClass().classLoader)
        executedTasks.clear()
        launcher.addListener(new TaskExecutionListener() {
            void beforeExecute(Task task) {
                executedTasks << new ExecutedTask(task: task)
            }

            void afterExecute(Task task, TaskState taskState) {
                executedTasks.last().state = taskState
            }
        })
        launcher
    }

    protected File getBuildFile() {
        file("build.gradle")
    }

    protected File directory(String path) {
        new File(dir.root, path).with {
            mkdirs()
            it
        }
    }

    protected File file(String path) {
        def splitted = path.split('/')
        def directory = splitted.size() > 1 ? directory(splitted[0..-2].join('/')) : dir.root
        def file = new File(directory, splitted[-1])
        file.createNewFile()
        file
    }

    protected boolean fileExists(String path) {
        new File(dir.root, path).exists()
    }

    protected ExecutedTask task(String name) {
        executedTasks.find { it.task.name == name }
    }

    protected Collection<ExecutedTask> tasks(String... names) {
        def tasks = executedTasks.findAll { it.task.name in names }
        assert tasks.size() == names.size()
        tasks
    }

    protected BuildResult runTasks(String... tasks) {
        BuildResult result = launcher(tasks).run()
        if (result.failure) {
            throw result.failure
        }
        result
    }

    protected Project projectForTasks(String... tasks) {
        runTasks(tasks).gradle.rootProject
    }
}
