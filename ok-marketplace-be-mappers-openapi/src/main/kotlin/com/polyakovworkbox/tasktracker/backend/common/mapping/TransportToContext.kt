package com.polyakovworkbox.tasktracker.backend.common.mapping

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.Debug
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.Measurability
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchFilter
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskRequest
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Mode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.general.RequestId
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime as DueTimeDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability as MeasurabilityDomain
import com.polyakovworkbox.tasktracker.backend.common.models.general.Debug as DebugDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter as SearchFilterDomain
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

fun BeContext.mapRequest(request: CreateTaskRequest) = apply {
    this.requestId = this.requestId.mapFrom(request.requestId)
    this.debug = this.debug.mapFrom(request.debug)
    this.requestTask = this.requestTask.mapFrom(request.task)
}

fun BeContext.mapRequest(request: ReadTaskRequest) = apply {
    this.requestId = this.requestId.mapFrom(request.requestId)
    this.debug = this.debug.mapFrom(request.debug)
    this.requestTaskId = this.requestTask.id.mapFrom(request.id)
}

fun BeContext.mapRequest(request: UpdateTaskRequest) = apply {
    this.requestId = this.requestId.mapFrom(request.requestId)
    this.debug = this.debug.mapFrom(request.debug)
    this.requestTask = this.requestTask.mapFrom(request.task)
}

fun BeContext.mapRequest(request: DeleteTaskRequest) = apply {
    this.requestId = this.requestId.mapFrom(request.requestId)
    this.debug = this.debug.mapFrom(request.debug)
    this.requestTaskId = this.requestTaskId.mapFrom(request.id)
}

fun BeContext.mapRequest(request: SearchTasksRequest) = apply {
    this.requestId = this.requestId.mapFrom(request.requestId)
    this.debug = this.debug.mapFrom(request.debug)
    this.searchFilter = this.searchFilter.mapFrom(request.searchFilter)
}

private fun RequestId.mapFrom(requestId: String?) : RequestId =
    RequestId(requestId ?: throw IllegalArgumentException())

fun DebugDomain.mapFrom(from: Debug?) : DebugDomain {
    val mode = from?.mode?.name

    return if (mode == null) {
        DebugDomain.DEFAULT
    } else {
        val validatedMode = Mode.valueOf(mode)
        val stub = Stub.valueOf(from.stub?.name ?: "NONE")
        if (validatedMode == Mode.PROD) {
            DebugDomain(validatedMode, Stub.NONE)
        } else {
            DebugDomain(validatedMode, stub)
        }
    }
}

fun Task.mapFrom(task: CreatableTask?) : Task {
    if(task == null) throw IllegalArgumentException()

    this.name = this.name.mapFrom(task.name)
    this.description = this.description.mapFrom(task.description)
    this.attainabilityDescription = this.attainabilityDescription.mapFrom(task.attainabilityDescription)
    this.relevanceDescription = this.relevanceDescription.mapFrom(task.relevanceDescription)
    this.dueTime = this.dueTime.mapFrom(task.dueTime)
    this.measurability = this.measurability.mapFrom(task.measurability)
    this.parent = this.parent.mapFrom(task.parent)
    this.children = this.children.mapFrom(task.children)

    return this
}

fun Task.mapFrom(task: UpdatableTask?) : Task {
    if(task == null) throw IllegalArgumentException()

    this.id = this.id.mapFrom(task.id)
    this.name = this.name.mapFrom(task.name)
    this.description = this.description.mapFrom(task.description)
    this.attainabilityDescription = this.attainabilityDescription.mapFrom(task.attainabilityDescription)
    this.relevanceDescription = this.relevanceDescription.mapFrom(task.relevanceDescription)
    this.dueTime = this.dueTime.mapFrom(task.dueTime)
    this.measurability = this.measurability.mapFrom(task.measurability)
    this.parent = this.parent.mapFrom(task.parent)
    this.children = this.children.mapFrom(task.children)

    return this
}

fun SearchFilterDomain.mapFrom(searchFilter: SearchFilter?) : SearchFilterDomain {
    if(searchFilter == null) throw IllegalArgumentException()

    this.nameFilter = this.nameFilter.mapFrom(searchFilter.nameFilter)
    this.descriptionFilter = this.descriptionFilter.mapFrom(searchFilter.descriptionFilter)
    this.attainabilityDescriptionFilter = this.attainabilityDescriptionFilter.mapFrom(searchFilter.attainabilityDescriptionFilter)
    this.relevanceDescriptionFilter = this.relevanceDescriptionFilter.mapFrom(searchFilter.relevanceDescriptionFilter)
    this.dueTimeFilter = this.dueTimeFilter.mapFrom(searchFilter.dueTimeFilter)
    this.dueTimeFilterEquality = this.dueTimeFilterEquality.mapFrom(searchFilter.dueTimeFilterEquality?.name)
    this.measurabilityDescriptionFilter = this.measurabilityDescriptionFilter.mapFrom(searchFilter.measurabilityDescriptionFilter)
    this.progressMarkFilter = this.progressMarkFilter.mapFrom(searchFilter.progressMarkFilter)
    this.progressMarkFilterEquality = this.progressMarkFilterEquality.mapFrom(searchFilter.progressMarkFilterEquality?.name)
    this.parentIdFilter = this.parentIdFilter.mapFrom(searchFilter.prentIdFilter)
    this.childIdFilter = this.childIdFilter.mapFrom(searchFilter.childIdFilter)

    return if (
        this.nameFilter == Name.NONE
        && this.descriptionFilter == Description.NONE
        && this.attainabilityDescriptionFilter == Description.NONE
        && this.relevanceDescriptionFilter == Description.NONE
        && this.dueTimeFilter == DueTimeDomain.NONE
        && this.measurabilityDescriptionFilter == Description.NONE
        && this.progressMarkFilter == Percent.NONE
        && this.parentIdFilter == TaskIdReference.NONE
        && this.childIdFilter == TaskIdReference.NONE
    ) {
        SearchFilterDomain.ALL
    } else
        this
}

private fun List<TaskIdReference>.mapFrom(children: List<String>?) : List<TaskIdReference> =
    if (children.isNullOrEmpty())
        emptyList()
    else
        children.map { TaskIdReference(it) }.toList()

private fun TaskId.mapFrom(id: String?) : TaskId =
    TaskId(id ?: throw IllegalArgumentException())

private fun TaskIdReference.mapFrom(id: String?) : TaskIdReference =
    if(id == null) TaskIdReference.NONE else TaskIdReference(id)

fun MeasurabilityDomain.mapFrom(measurability: Measurability?) : MeasurabilityDomain =
    if (measurability == null)
        MeasurabilityDomain.NONE
    else
        MeasurabilityDomain(
            this.description.mapFrom(measurability.description),
            this.progress.mapFrom(measurability.progressMark)
        )

fun DueTimeDomain.mapFrom(dueTime: String?) : DueTimeDomain =
    if (dueTime == null) DueTimeDomain.NONE else DueTimeDomain(LocalDateTime.parse(dueTime))

fun Description.mapFrom(description: String?) : Description =
    if (description == null) Description.NONE else Description(description)

fun Percent.mapFrom(progressMark: Int?) : Percent =
    if (progressMark == null) Percent.NONE else Percent(progressMark)

fun Name.mapFrom(name: String?) : Name =
    if (name == null) Name.NONE else Name(name)

fun EqualityMode.mapFrom(equalityMode: String?) : EqualityMode =
    if(equalityMode == null) EqualityMode.NONE else EqualityMode.valueOf(equalityMode)


