package com.polyakovworkbox.tasktracker.backend.common.models.task

import java.time.LocalDateTime

@JvmInline
value class DueTime(
    val dueTime: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        val NONE = DueTime()
    }
}