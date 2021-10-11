package com.polyakovworkbox.tasktracker.springapp.services

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ResponseResult
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskResponse
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.context.ResponseStatus
import com.polyakovworkbox.tasktracker.backend.common.mapping.mapRequest
import com.polyakovworkbox.tasktracker.backend.common.mapping.toCreateResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toDeleteResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toErrorResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toReadResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toSearchResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toUpdateResponse
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.logics.TaskCrud
import com.polyakovworkbox.tasktracker.stubs.TaskStub

open class TaskService(
    var crud: TaskCrud
) {

    suspend fun create(context: BeContext, request: CreateTaskRequest): BaseResponse {
        crud.create(context.mapRequest(request))
        return context.toCreateResponse()

/*
        return if(TaskStub.isCreatedSuccessfully()) {
            context.mapRequest(request).apply {
                this.responseTask = TaskStub.getModel()
            }.toCreateResponse()
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task could not be created"
                    )
                )
            }.toErrorResponse(::CreateTaskResponse)
        }
*/

    }

    suspend fun read(context: BeContext, request: ReadTaskRequest): BaseResponse {
        crud.read(context.mapRequest(request))
        return context.toReadResponse()

/*        context.mapRequest(request)
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
            }.toErrorResponse(::ReadTaskResponse)
        }*/
    }

    suspend fun update(context: BeContext, request: UpdateTaskRequest): BaseResponse {
        crud.update(context.mapRequest(request))
        return context.toUpdateResponse()

/*        return if (TaskStub.isUpdatedSuccessfully()) {
            context.mapRequest(request).apply {
                this.responseTask = TaskStub.getModelUpdated(context)
            }.toUpdateResponse()
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task with id ${context.requestTaskId} couldn't be updated"
                    )
                )
            }.toErrorResponse(::UpdateTaskResponse)
        }*/
    }


    suspend fun delete(context: BeContext, request: DeleteTaskRequest): BaseResponse {
        crud.delete(context.mapRequest(request))
        return context.toDeleteResponse()

/*        context.mapRequest(request)
        val requestedId = context.requestTaskId.id

        return if (TaskStub.isCorrectId(requestedId)) {
            context.apply {
                responseTask = TaskStub.getDeletedModel(context)
            }.toDeleteResponse()
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task with id $requestedId cannot be found"
                    )
                )
            }.toErrorResponse(::DeleteTaskResponse)
        }*/
    }

    suspend fun search(context: BeContext, request: SearchTasksRequest): BaseResponse {
        crud.search(context.mapRequest(request))
        return context.toSearchResponse()

/*        context.mapRequest(request)

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
            }.toErrorResponse(::SearchTasksResponse)
        }*/
    }

    suspend inline fun <reified T : BaseResponse> toError(context: BeContext, e: Throwable,
        createResponse: (String, String?, ResponseResult?, List<com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ApiError>) -> T) : BaseResponse {
        context.addError(e)
        return context.toErrorResponse(createResponse)
    }
}
