package com.polyakovworkbox.tasktracker.backend.common.mapping

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdatableTask
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId


//mapping request to context
fun BeContext.setQuery(query: CreateTaskRequest) = apply {
    this.requestId = query.requestId ?: ""
    this.requestTask.name = Name(query.createTask?.name ?: "")
    this.requestTask.description = Description(query.createTask?.description ?: "")
}

fun BeContext.setQuery(query: ReadTaskRequest) = apply {
    this.requestTask.id = TaskId(query.id ?: "")
}

//mapping of object types to domain objects
private fun UpdatableTask.toModel() = Task(
        name = Name(this.name),
        description = Description(this.description ?: ""),
        id = TaskId(this.id)
)