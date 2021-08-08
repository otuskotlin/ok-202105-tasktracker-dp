package com.polyakovworkbox.tasktracker.backend.common.models.task

@JvmInline
value class Description(
    val description: String = ""
) {
    companion object {
        val NONE = Description()
    }
}