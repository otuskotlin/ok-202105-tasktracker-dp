package com.polyakovworkbox.tasktracker.springapp.services

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseMessage
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ResponseResult
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskResponse
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.mapping.mapRequest
import com.polyakovworkbox.tasktracker.backend.common.mapping.toCreateResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toDeleteResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toErrorResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toLog
import com.polyakovworkbox.tasktracker.backend.common.mapping.toReadResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toSearchResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toUpdateResponse
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.logging.loggerFor
import com.polyakovworkbox.tasktracker.backend.logics.TaskCrud
import org.slf4j.event.Level
import java.lang.IllegalArgumentException

open class TaskService(
    var crud: TaskCrud
) {

    private val logger = loggerFor(this::class.java)

    suspend fun create(context: BeContext, request: CreateTaskRequest): BaseResponse {
        context.mapRequest(request)

        return context.handle("createTask", BeContext::toCreateResponse) {
            crud.create(it)
        }
    }

    suspend fun read(context: BeContext, request: ReadTaskRequest): BaseResponse {
        context.mapRequest(request)

        return context.handle("readTask", BeContext::toReadResponse) {
            crud.read(it)
        }
    }

    suspend fun update(context: BeContext, request: UpdateTaskRequest): BaseResponse {
        context.mapRequest(request)

        return context.handle("updateTask", BeContext::toUpdateResponse) {
            crud.update(it)
        }
    }

    suspend fun delete(context: BeContext, request: DeleteTaskRequest): BaseResponse {
        context.mapRequest(request)

        return context.handle("deleteTask", BeContext::toDeleteResponse) {
            crud.delete(it)
        }
    }

    suspend fun search(context: BeContext, request: SearchTasksRequest): BaseResponse {
        context.mapRequest(request)

        return context.handle("searchTask", BeContext::toSearchResponse) {
            crud.search(it)
        }
    }

    suspend inline fun <reified T : BaseResponse> toError(context: BeContext, e: Throwable,
        createResponse: (String, String?, ResponseResult?, List<com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ApiError>) -> T) : BaseResponse {
        context.addError(e)
        return context.toErrorResponse(createResponse)
    }

    suspend fun handleAsyncTaskRequest(beContext: BeContext, baseMessage: BaseMessage) =
        when (baseMessage) {
            is CreateTaskRequest -> {
                try {
                    beContext.operation = Operation.CREATE
                    create(beContext, baseMessage)
                } catch (e: Throwable) {
                    toError(beContext, e, ::CreateTaskResponse)
                 }
            }
            is ReadTaskRequest -> {
                try {
                    beContext.operation = Operation.READ
                    read(beContext, baseMessage)
                } catch (e: Throwable) {
                    toError(beContext, e, ::CreateTaskResponse)
                }
            }
            is UpdateTaskRequest -> {
                try {
                    beContext.operation = Operation.UPDATE
                    update(beContext, baseMessage)
                } catch (e: Throwable) {
                    toError(beContext, e, ::UpdateTaskResponse)
                }
            }
            is DeleteTaskRequest -> {
                try {
                    beContext.operation = Operation.DELETE
                    delete(beContext, baseMessage)
                } catch (e: Throwable) {
                    toError(beContext, e, ::DeleteTaskResponse)
                }
            }
            is SearchTasksRequest -> {
                try {
                    beContext.operation = Operation.SEARCH
                    search(beContext, baseMessage)
                } catch (e: Throwable) {
                    toError(beContext, e, ::SearchTasksResponse)
                }
            }
            else -> throw IllegalArgumentException("Request is not Allowed: $baseMessage")
        }

    private suspend fun <T> BeContext.handle(
        logId: String,
        mapper: BeContext.() -> T,
        block: suspend (BeContext) -> Unit = {}
    ): T {
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("$logId-request-got")
        )
        block(this)
        return mapper().also {
            logger.log(
                msg = "Response ready, response = {}",
                level = Level.INFO,
                data = toLog("$logId-request-handled")
            )
        }
    }
}