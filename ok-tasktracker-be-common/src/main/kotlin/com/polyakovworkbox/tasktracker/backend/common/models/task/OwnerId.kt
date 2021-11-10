package com.polyakovworkbox.tasktracker.backend.common.models.task

import java.util.*

@JvmInline
value class OwnerId(
    val id: String
) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = OwnerId("")
    }

    fun asString() = id

    fun asUUID(): UUID = if (id.isNotBlank()) UUID.fromString(id) else throw IllegalStateException()
}