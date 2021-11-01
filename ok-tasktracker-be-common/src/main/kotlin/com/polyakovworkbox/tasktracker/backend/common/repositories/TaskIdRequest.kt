package com.polyakovworkbox.tasktracker.backend.common.repositories

import java.util.*

data class TaskIdRequest(
    val id: String = ""
): ITaskRepoRequest {

    constructor(id: UUID) : this(id.toString())

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}