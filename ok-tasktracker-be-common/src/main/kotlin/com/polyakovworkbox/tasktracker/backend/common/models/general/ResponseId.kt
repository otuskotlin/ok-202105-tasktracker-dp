package com.polyakovworkbox.tasktracker.backend.common.models.general

import java.util.*

@JvmInline
value class ResponseId(
    val id: String
) {
    companion object {
        val NONE = ResponseId("")
    }

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}