package com.polyakovworkbox.tasktracker.backend.common.models.general

import java.util.*

@JvmInline
value class RequestId(
    val id: String
) {
    companion object {
        val NONE = RequestId("")
    }

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}