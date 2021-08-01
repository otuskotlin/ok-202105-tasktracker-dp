package com.polyakovworkbox.tasktracker.backend.common.mapping

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdatableTask
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.UpdatableTask as domainUpdatableTask


//mapping request to context
fun BeContext.setQuery(query: CreateTaskRequest) = apply {
    this.requestId = query.requestId ?: ""
    this.requestTask.task.name = query.createTask?.name ?: ""
    this.requestTask.task.description = query.createTask?.description ?: ""
}

fun BeContext.setQuery(query: ReadTaskRequest) = apply {
    this.requestTask.task.id = query.id ?: ""
}

//mapping of object types to domain objects
private fun UpdatableTask.toModel() = domainUpdatableTask(
        name = this.name ?: "",
        description = this.description ?: "",
        id = this.id ?: ""
)