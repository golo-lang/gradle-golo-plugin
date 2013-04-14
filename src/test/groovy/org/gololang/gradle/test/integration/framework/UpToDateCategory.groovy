package org.gololang.gradle.test.integration.framework

import org.gradle.api.internal.tasks.TaskStateInternal

@Category(TaskStateInternal)
class UpToDateCategory {
    boolean isUpToDate() {
        skipped && skipMessage == 'UP-TO-DATE'
    }
}
