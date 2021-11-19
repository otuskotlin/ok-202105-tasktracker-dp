package com.polyakovworkbox.tasktracker.backend.common.repositories

import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import java.util.*

data class TaskIdRequest(
    val id: String = ""
): ITaskRepoRequest {

    companion object {
        fun getRandom(): TaskIdRequest = TaskIdRequest(UUID.randomUUID())
    }

    constructor(id: UUID) : this(id.toString())

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}