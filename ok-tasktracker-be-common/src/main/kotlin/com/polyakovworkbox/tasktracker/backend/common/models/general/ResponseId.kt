package com.polyakovworkbox.tasktracker.backend.common.models.general

@JvmInline
value class ResponseId(
    val id: String
) {
    companion object {
        val NONE = ResponseId("")
    }
}