package com.polyakovworkbox.tasktracker.backend.common.models.task

@JvmInline
value class TaskId(
    val id: String
) {
    companion object {
        val NONE = TaskId("")
    }
}