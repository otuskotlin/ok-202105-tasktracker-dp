package com.polyakovworkbox.tasktracker.backend.common.models.general

@JvmInline
value class RequestId(
    val id: String
) {
    companion object {
        val NONE = RequestId("")
    }
}