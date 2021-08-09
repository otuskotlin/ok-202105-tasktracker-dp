package com.polyakovworkbox.tasktracker.backend.common.mapping

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ApiError
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.Measurability
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ResponseResult
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksResponse
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError as DomainApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task as TaskDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability as MeasurabilityDomain


fun BeContext.toCreateResponse() = CreateTaskResponse(
    responseId = this.responseId.id,
    messageType = CreateTaskResponse::class.java.simpleName,
    result = if(errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.mapErrorsToTransport(),
    task = if(errors.isEmpty()) this.responseTask.mapToTransport() else null
)

fun BeContext.toReadResponse() = ReadTaskResponse(
    responseId = this.responseId.id,
    messageType = ReadTaskResponse::class.java.simpleName,
    result = if(errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.mapErrorsToTransport(),
    task = if(errors.isEmpty()) this.responseTask.mapToTransport() else null
)

fun BeContext.toUpdateResponse() = UpdateTaskResponse(
    responseId = this.responseId.id,
    messageType = UpdateTaskResponse::class.java.simpleName,
    result = if(errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.mapErrorsToTransport(),
    task = if(errors.isEmpty()) this.responseTask.mapToTransport() else null
)

fun BeContext.toDeleteResponse() = DeleteTaskResponse(
    responseId = this.responseId.id,
    messageType = DeleteTaskResponse::class.java.simpleName,
    result = if(errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.mapErrorsToTransport(),
    task = if(errors.isEmpty()) this.responseTask.mapToTransport() else null
)

fun BeContext.toSearchResponse() = SearchTasksResponse(
    responseId = this.responseId.id,
    messageType = SearchTasksResponse::class.java.simpleName,
    result = if(errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = this.errors.mapErrorsToTransport(),
    availableTasks = if(errors.isEmpty()) this.responseTasks.mapTasksToTransport() else null
)

private fun MutableList<TaskDomain>.mapTasksToTransport(): List<UpdatableTask> =
    this.map { it.mapToTransport() }.toList()

fun MutableList<DomainApiError>.mapErrorsToTransport(): List<ApiError> =
    this.map { it.mapToTransport() }.toList()

fun com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError.mapToTransport() : ApiError =
    ApiError(this.message, this.field)

fun TaskDomain.mapToTransport(): UpdatableTask =
    UpdatableTask(
        name = this.name.name,
        id = this.id.id,
        description = this.description.description,
        attainabilityDescription = this.attainabilityDescription.description,
        relevanceDescription = this.relevanceDescription.description,
        measurability = this.measurability.mapToTransport(),
        dueTime = this.dueTime.dueTime.toString(),
        parent = this.parent.id,
        children = this.children.mapChildrenToTransport()
    )


private fun List<TaskIdReference>.mapChildrenToTransport(): List<String> =
    this.map { it.id }.toList()


private fun MeasurabilityDomain.mapToTransport(): Measurability =
    Measurability(
        description = this.description.description,
        progressMark = this.progress.percent
    )

