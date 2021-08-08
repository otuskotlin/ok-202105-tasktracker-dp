package com.polyakovworkbox.tasktracker.backend.common.models.task

@JvmInline
value class Name(
    val name: String = ""
) {
    companion object {
        val NONE = Name()
    }
}