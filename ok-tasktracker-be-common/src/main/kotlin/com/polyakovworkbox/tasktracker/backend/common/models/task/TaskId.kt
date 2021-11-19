package com.polyakovworkbox.tasktracker.backend.common.models.task

import java.util.*

@JvmInline
value class TaskId(
    val id: String
) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = TaskId("")

        fun getRandom(): TaskId = TaskId(UUID.randomUUID())
    }

    fun asString() = id

    fun asUUID(): UUID = if (id.isNotBlank()) UUID.fromString(id) else throw IllegalStateException()
}