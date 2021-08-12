package com.polyakovworkbox.tasktracker.backend.common.models.task

import java.time.Instant

@JvmInline
value class DueTime(
    val dueTime: Instant = Instant.now()
) {
    companion object {
        val NONE = DueTime()
    }
}