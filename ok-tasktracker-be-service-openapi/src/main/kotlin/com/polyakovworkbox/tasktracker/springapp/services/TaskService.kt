package com.polyakovworkbox.tasktracker.springapp.services

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskResponse
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.context.ResponseStatus
import com.polyakovworkbox.tasktracker.backend.common.mapping.mapRequest
import com.polyakovworkbox.tasktracker.backend.common.mapping.toCreateResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toDeleteResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toReadResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toSearchResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toUpdateResponse
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.stubs.TaskStub

open class TaskService {

    suspend fun create(context: BeContext, request: CreateTaskRequest): CreateTaskResponse =
        context.mapRequest(request).apply {
            this.responseTask = TaskStub.getModel()
        }.toCreateResponse()


    suspend fun read(context: BeContext, request: ReadTaskRequest): ReadTaskResponse {
        context.mapRequest(request)
        val requestedId = context.requestTaskId.id

        return if (TaskStub.isCorrectId(requestedId)) {
            context.apply {
                responseTask = TaskStub.getModel()
            }.toReadResponse()
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task with id $requestedId cannot be found"
                    )
                )
            }.toReadResponse()
        }
    }

    suspend fun update(context: BeContext, request: UpdateTaskRequest): UpdateTaskResponse =
        context.mapRequest(request).apply {
            this.responseTask = TaskStub.getModelUpdated(context)
        }.toUpdateResponse()

    suspend fun delete(context: BeContext, request: DeleteTaskRequest): DeleteTaskResponse {
        context.mapRequest(request)
        val requestedId = context.requestTaskId.id

        return if (TaskStub.isCorrectId(requestedId)) {
            context.apply {
                responseTask = requestTask
            }.toDeleteResponse()
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task with id $requestedId cannot be found"
                    )
                )
            }.toDeleteResponse()
        }
    }

    suspend fun search(context: BeContext, request: SearchTasksRequest): SearchTasksResponse {
        context.mapRequest(request)

        return if (TaskStub.taskWithCriteriaExists(context.searchFilter)) {
            context.apply {
                responseTasks = mutableListOf(TaskStub.getModel())
            }.toSearchResponse()
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task with given criteria cannot be found"
                    )
                )
            }.toSearchResponse()
        }
    }

    suspend fun createError(context: BeContext, e: Throwable) : CreateTaskResponse {
        context.addError(e)
        return context.toCreateResponse()
    }

    suspend fun readError(context: BeContext, e: Throwable) : ReadTaskResponse {
        context.addError(e)
        return context.toReadResponse()
    }

    suspend fun updateError(context: BeContext, e: Throwable) : UpdateTaskResponse {
        context.addError(e)
        return context.toUpdateResponse()
    }

    suspend fun deleteError(context: BeContext, e: Throwable) : DeleteTaskResponse {
        context.addError(e)
        return context.toDeleteResponse()
    }

    suspend fun searchError(context: BeContext, e: Throwable) : SearchTasksResponse {
        context.addError(e)
        return context.toSearchResponse()
    }
}
