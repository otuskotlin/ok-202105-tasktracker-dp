package com.polyakovworkbox.tasktracker.backend.common.mapping

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ApiError
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CommonLogModel
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.EqualityMode
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.LogModel
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.Measurability
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ResponseResult
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksResponse
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.context.ResponseStatus
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchFilter
import java.time.Instant
import java.util.*
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError as DomainApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task as TaskDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability as MeasurabilityDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter as SearchFilterDomain


fun BeContext.toCreateResponse() = CreateTaskResponse(
    responseId = responseId.id,
    messageType = CreateTaskResponse::class.java.simpleName,
    result =
    if(errors.isEmpty() && status != ResponseStatus.ERROR)
        ResponseResult.SUCCESS
    else ResponseResult.ERROR,
    errors = errors.mapErrorsToTransport(),
    task = responseTask.takeIf { errors.isEmpty() }?.mapToTransport()
)

fun BeContext.toReadResponse() = ReadTaskResponse(
    responseId = responseId.id,
    messageType = ReadTaskResponse::class.java.simpleName,
    result =
    if(errors.isEmpty() && status != ResponseStatus.ERROR)
        ResponseResult.SUCCESS
    else ResponseResult.ERROR,
    errors = errors.mapErrorsToTransport(),
    task = responseTask.takeIf { errors.isEmpty() }?.mapToTransport()
)

fun BeContext.toUpdateResponse() = UpdateTaskResponse(
    responseId = responseId.id,
    messageType = UpdateTaskResponse::class.java.simpleName,
    result =
    if(errors.isEmpty() && status != ResponseStatus.ERROR)
        ResponseResult.SUCCESS
    else ResponseResult.ERROR,
    errors = errors.mapErrorsToTransport(),
    task = responseTask.takeIf { errors.isEmpty() }?.mapToTransport()
)

fun BeContext.toDeleteResponse() = DeleteTaskResponse(
    responseId = responseId.id,
    messageType = DeleteTaskResponse::class.java.simpleName,
    result =
    if(errors.isEmpty() && status != ResponseStatus.ERROR)
        ResponseResult.SUCCESS
    else ResponseResult.ERROR,
    errors = errors.mapErrorsToTransport(),
    task = responseTask.takeIf { errors.isEmpty() }?.mapToTransport()
)

fun BeContext.toSearchResponse() = SearchTasksResponse(
    responseId = responseId.id,
    messageType = SearchTasksResponse::class.java.simpleName,
    result =
    if(errors.isEmpty() && status != ResponseStatus.ERROR)
        ResponseResult.SUCCESS
    else ResponseResult.ERROR,
    errors = errors.mapErrorsToTransport(),
    availableTasks = responseTasks.takeIf { errors.isEmpty() }?.mapTasksToTransport()
)

inline fun <reified T : BaseResponse>BeContext.toErrorResponse(createResponse: (String, String?, ResponseResult?, List<ApiError>) -> T): T =
    createResponse(
        this.responseId.id,
        T::class.java.simpleName,
        ResponseResult.ERROR,
        errors.mapErrorsToTransport())

private fun MutableList<TaskDomain>.mapTasksToTransport(): List<UpdatableTask> =
    map { it.mapToTransport() }.toList()

fun MutableList<DomainApiError>.mapErrorsToTransport(): List<ApiError> =
    map { it.mapToTransport() }.toList()

fun com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError.mapToTransport() : ApiError =
    ApiError(message, field)

fun TaskDomain.mapToTransport(): UpdatableTask =
    UpdatableTask(
        name = name.name,
        ownerId = ownerId.id,
        id = id.mapToTransport(),
        description = description.description,
        attainabilityDescription = attainabilityDescription.description,
        relevanceDescription = relevanceDescription.description,
        measurability = measurability.mapToTransport(),
        dueTime = dueTime.dueTime.toString(),
        parent = parent.mapToTransport(),
        children = children.map { it.id }.toList()
    )

private fun TaskId.mapToTransport(): String = id

private fun TaskIdReference.mapToTransport(): String = id

private fun MeasurabilityDomain.mapToTransport(): Measurability =
    Measurability(
        description = description.description,
        progressMark = progress.percent
    )

private fun SearchFilterDomain.mapToTransport(): SearchFilter =
    SearchFilter(
        nameFilter = this.nameFilter.name,
        descriptionFilter = this.descriptionFilter.description,
        attainabilityDescriptionFilter = this.attainabilityDescriptionFilter.description,
        relevanceDescriptionFilter = this.relevanceDescriptionFilter.description,
        dueTimeFilter = this.dueTimeFilter.dueTime.toString(),
        dueTimeFilterEquality = EqualityMode.valueOf(this.dueTimeFilterEquality.name),
        measurabilityDescriptionFilter = this.measurabilityDescriptionFilter.description,
        progressMarkFilter = this.progressMarkFilter.percent,
        progressMarkFilterEquality = EqualityMode.valueOf(this.progressMarkFilterEquality.name),
        parentIdFilter = this.parentIdFilter.id,
        childIdFilter = this.childIdFilter.id
    )

fun BeContext.toLog(logId: String) = CommonLogModel(
    messageId = UUID.randomUUID().toString(),
    messageTime = Instant.now().toString(),
    logId = logId,
    source = "ok-tasktracker",
    task = LogModel(
        requestTaskId = requestTaskId.takeIf { it != TaskId.NONE }?.id,
        requestTask = requestTask.takeIf { it != com.polyakovworkbox.tasktracker.backend.common.models.task.Task() }?.mapToTransport(),
        searchFilter = searchFilter.mapToTransport(),
        responseTasks = responseTasks.mapTasksToTransport().takeIf { it.isNotEmpty() } ?: mutableListOf(responseTask).mapTasksToTransport()
    ),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.mapToTransport() }

)

