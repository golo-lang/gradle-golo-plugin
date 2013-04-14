package org.gololang.gradle.test.integration.framework

import org.gradle.api.Task
import org.gradle.api.internal.tasks.TaskStateInternal

class ExecutedTask {
    Task task
    TaskStateInternal state

    String toString() {
        "executed $task"
    }
}
