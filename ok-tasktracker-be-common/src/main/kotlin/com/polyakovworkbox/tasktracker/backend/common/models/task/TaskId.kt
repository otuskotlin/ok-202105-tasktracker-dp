package com.polyakovworkbox.tasktracker.backend.common.models.task

import java.util.*

@JvmInline
value class TaskId(
    val id: String
) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = TaskId("")
    }

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}