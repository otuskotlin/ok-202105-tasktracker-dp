package com.polyakovworkbox.tasktracker.backend.common.models.task

@JvmInline
value class TaskIdReference (
    val id: String
) {
    companion object {
        val NONE = TaskIdReference("")
    }
}

