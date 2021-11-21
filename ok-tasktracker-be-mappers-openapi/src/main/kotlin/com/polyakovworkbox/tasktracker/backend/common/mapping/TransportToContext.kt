package com.polyakovworkbox.tasktracker.backend.common.mapping

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.Debug
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.EqualityMode
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.Measurability
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchFilter
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskRequest
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Mode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.general.RequestId
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime as DueTimeDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability as MeasurabilityDomain
import com.polyakovworkbox.tasktracker.backend.common.models.general.Debug as DebugDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.OwnerId
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter as SearchFilterDomain
import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode as EqualityModeDomain
import java.lang.IllegalArgumentException
import java.time.Instant

fun BeContext.mapRequest(request: CreateTaskRequest) = apply {
    requestId = requestId.mapFrom(request.requestId)
    debug = debug.mapFrom(request.debug)
    requestTask = requestTask.mapFrom(request.task)
}

fun BeContext.mapRequest(request: ReadTaskRequest) = apply {
    requestId = requestId.mapFrom(request.requestId)
    debug = debug.mapFrom(request.debug)
    requestTaskId = requestTask.id.mapFrom(request.id)
}

fun BeContext.mapRequest(request: UpdateTaskRequest) = apply {
    requestId = requestId.mapFrom(request.requestId)
    debug = debug.mapFrom(request.debug)
    requestTask = requestTask.mapFrom(request.task)
}

fun BeContext.mapRequest(request: DeleteTaskRequest) = apply {
    requestId = requestId.mapFrom(request.requestId)
    debug = debug.mapFrom(request.debug)
    requestTaskId = requestTaskId.mapFrom(request.id)
}

fun BeContext.mapRequest(request: SearchTasksRequest) = apply {
    requestId = requestId.mapFrom(request.requestId)
    debug = debug.mapFrom(request.debug)
    searchFilter = searchFilter.mapFrom(request.searchFilter)
}

private fun RequestId.mapFrom(requestId: String?) : RequestId =
    RequestId(requestId ?: throw IllegalArgumentException())

fun DebugDomain.mapFrom(from: Debug?) : DebugDomain {
    val mode = from?.mode

    return if (mode == null) {
        DebugDomain.DEFAULT
    } else {
        val validatedMode =
            when (mode) {
                Debug.Mode.PROD -> Mode.PROD
                Debug.Mode.STUB -> Mode.STUB
                Debug.Mode.TEST -> Mode.TEST
            }
        val validatedStub =
            when (from.stub) {
                null -> Stub.NONE
                Debug.Stub.NONE -> Stub.NONE
                Debug.Stub.SUCCESS -> Stub.SUCCESS
                Debug.Stub.ERROR_DB -> Stub.ERROR_DB
            }
        if (validatedMode != Mode.STUB) {
            DebugDomain(validatedMode, Stub.NONE)
        } else {
            DebugDomain(validatedMode, validatedStub)
        }
    }
}

fun Task.mapFrom(task: CreatableTask?) : Task {
    if(task == null) throw IllegalArgumentException()

    name = name.mapFrom(task.name)
    ownerId = ownerId.mapFrom(task.ownerId)
    description = description.mapFrom(task.description)
    attainabilityDescription = attainabilityDescription.mapFrom(task.attainabilityDescription)
    relevanceDescription = relevanceDescription.mapFrom(task.relevanceDescription)
    dueTime = dueTime.mapFrom(task.dueTime)
    measurability = measurability.mapFrom(task.measurability)
    parent = parent.mapFrom(task.parent)
    children = children.mapFrom(task.children)

    return this
}

fun Task.mapFrom(task: UpdatableTask?) : Task {
    if(task == null) throw IllegalArgumentException()

    id = id.mapFrom(task.id)
    ownerId = ownerId.mapFrom(task.ownerId)
    name = name.mapFrom(task.name)
    description = description.mapFrom(task.description)
    attainabilityDescription = attainabilityDescription.mapFrom(task.attainabilityDescription)
    relevanceDescription = relevanceDescription.mapFrom(task.relevanceDescription)
    dueTime = dueTime.mapFrom(task.dueTime)
    measurability = measurability.mapFrom(task.measurability)
    parent = parent.mapFrom(task.parent)
    children = children.mapFrom(task.children)

    return this
}

fun SearchFilterDomain.mapFrom(searchFilter: SearchFilter?) : SearchFilterDomain {
    if(searchFilter == null) throw IllegalArgumentException()

    nameFilter = nameFilter.mapFrom(searchFilter.nameFilter)
    descriptionFilter = descriptionFilter.mapFrom(searchFilter.descriptionFilter)
    attainabilityDescriptionFilter = attainabilityDescriptionFilter.mapFrom(searchFilter.attainabilityDescriptionFilter)
    relevanceDescriptionFilter = relevanceDescriptionFilter.mapFrom(searchFilter.relevanceDescriptionFilter)
    dueTimeFilter = dueTimeFilter.mapFrom(searchFilter.dueTimeFilter)
    dueTimeFilterEquality = dueTimeFilterEquality.mapFrom(searchFilter.dueTimeFilterEquality)
    measurabilityDescriptionFilter = measurabilityDescriptionFilter.mapFrom(searchFilter.measurabilityDescriptionFilter)
    progressMarkFilter = progressMarkFilter.mapFrom(searchFilter.progressMarkFilter)
    progressMarkFilterEquality = progressMarkFilterEquality.mapFrom(searchFilter.progressMarkFilterEquality)
    parentIdFilter = parentIdFilter.mapFrom(searchFilter.parentIdFilter)
    childIdFilter = childIdFilter.mapFrom(searchFilter.childIdFilter)

    return this
}

private fun List<TaskIdReference>.mapFrom(children: List<String>?) : List<TaskIdReference> =
    if (children.isNullOrEmpty())
        emptyList()
    else
        children.map { TaskIdReference(it) }.toList()

private fun TaskId.mapFrom(id: String?) : TaskId =
    TaskId(id ?: throw IllegalArgumentException())

private fun OwnerId.mapFrom(id: String?) : OwnerId =
    OwnerId(id ?: throw IllegalArgumentException())

private fun TaskIdReference.mapFrom(id: String?) : TaskIdReference =
    if(id == null) TaskIdReference.NONE else TaskIdReference(id)

fun MeasurabilityDomain.mapFrom(measurability: Measurability?) : MeasurabilityDomain =
    if (measurability == null)
        MeasurabilityDomain()
    else
        MeasurabilityDomain(
            description.mapFrom(measurability.description),
            progress.mapFrom(measurability.progressMark)
        )

fun DueTimeDomain.mapFrom(dueTime: String?) : DueTimeDomain =
    if (dueTime == null) DueTimeDomain.NONE else DueTimeDomain(Instant.parse(dueTime))

fun Description.mapFrom(description: String?) : Description =
    if (description == null) Description.NONE else Description(description)

fun Percent.mapFrom(progressMark: Int?) : Percent =
    if (progressMark == null) Percent.NONE else Percent(progressMark)

fun Name.mapFrom(name: String?) : Name =
    if (name == null) Name.NONE else Name(name)

fun EqualityModeDomain.mapFrom(equalityMode: EqualityMode?) : EqualityModeDomain =
    when (equalityMode) {
        null -> EqualityModeDomain.NONE
        EqualityMode.NONE -> EqualityModeDomain.NONE
        EqualityMode.MORE_THAN -> EqualityModeDomain.MORE_THAN
        EqualityMode.LESS_THAN -> EqualityModeDomain.LESS_THAN
        EqualityMode.EQUALS -> EqualityModeDomain.EQUALS
    }


