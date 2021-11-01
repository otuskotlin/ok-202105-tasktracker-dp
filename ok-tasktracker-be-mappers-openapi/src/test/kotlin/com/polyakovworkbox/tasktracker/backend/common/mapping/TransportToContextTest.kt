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
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import java.time.Instant
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability as MeasurabilityDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task as TaskDomain
import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode as EqualityModeDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter as SearchFilterDomain
import kotlin.test.Test
import kotlin.test.assertEquals
import com.polyakovworkbox.tasktracker.backend.common.models.general.Mode as ModeDomain
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub as StubDomain
import com.polyakovworkbox.tasktracker.backend.common.models.general.Debug as DebugDomain

internal class TransportToContextTest {

    @Test
    fun `Debug mapping - debug mode is used`() {
        val debug = Debug(
            mode = Debug.Mode.STUB,
            stub = Debug.Stub.SUCCESS
        )

        val debugDomain = DebugDomain().mapFrom(debug)

        assertEquals(debugDomain.mode, ModeDomain.STUB)
        assertEquals(debugDomain.stub, StubDomain.SUCCESS)
    }

    @Test
    fun `Debug mapping - debug is not provided and default is assumed`() {
        val debug = null

        val debugDomain = DebugDomain().mapFrom(debug)

        assertEquals(debugDomain, DebugDomain.DEFAULT)
    }

    @Test
    fun `Debug mapping - debug is provided but mode and stub are nulls`() {
        val debug = Debug(
            mode = null,
            stub = null
        )

        val debugDomain = DebugDomain().mapFrom(debug)

        assertEquals(debugDomain, DebugDomain.DEFAULT)
    }

    @Test
    fun `Debug mapping - debug mode is not used but stub value is provided and must be ignored`() {
        val debug = Debug(
            mode = Debug.Mode.PROD,
            stub = Debug.Stub.ERROR_DB
        )

        val debugDomain = DebugDomain().mapFrom(debug)

        assertEquals(debugDomain.mode, ModeDomain.PROD)
        assertEquals(debugDomain.stub, StubDomain.NONE)
    }

    @Test
    fun `create task operation mapping test`() {
        val request = CreateTaskRequest(
                requestId = "request-id",
                task = CreatableTask(
                    name = "some-name",
                    description = "some-description"
                )
        )

        val context = BeContext().mapRequest(request)

        assertEquals("request-id", context.requestId.id)
        assertEquals("some-name", context.requestTask.name.name)
        assertEquals("some-description", context.requestTask.description.description)
    }

    @Test
    fun `read task operation mapping test`() {
        val request = ReadTaskRequest(
            requestId = "request-id",
            id = "some-id"
        )

        val context = BeContext().mapRequest(request)

        assertEquals("request-id", context.requestId.id)
        assertEquals("some-id", context.requestTaskId.id)
    }

    @Test
    fun `update task operation mapping test`() {
        val request = UpdateTaskRequest(
            requestId = "request-id",
            task = UpdatableTask(
                id = "some-id",
                name = "some-name",
                description = "some-new-description"
            )
        )

        val context = BeContext().mapRequest(request)

        assertEquals("request-id", context.requestId.id)
        assertEquals("some-id", context.requestTask.id.id)
        assertEquals("some-name", context.requestTask.name.name)
        assertEquals("some-new-description", context.requestTask.description.description)
    }

    @Test
    fun `delete task operation mapping test`() {
        val request = DeleteTaskRequest(
            requestId = "request-id",
            id = "some-id"
        )

        val context = BeContext().mapRequest(request)

        assertEquals("request-id", context.requestId.id)
        assertEquals("some-id", context.requestTaskId.id)
    }

    @Test
    fun `search task operation mapping test`() {
        val request = SearchTasksRequest(
            requestId = "request-id",
            searchFilter = SearchFilter()
        )

        val context = BeContext().mapRequest(request)

        assertEquals("request-id", context.requestId.id)
        assertEquals(SearchFilterDomain(), context.searchFilter)
    }

    @Test
    fun `creatable task mapping test`() {
        val creatableTask = CreatableTask(
            name = "some-name",
            description = "some-description",
            attainabilityDescription = "some-attainability-description",
            relevanceDescription = "some-relevance-description",
            measurability = null,
            dueTime = null,
            parent = "parent-task-id",
            children = listOf("child-task-id-1", "child-task-id-2")
        )

        val mappedTask = TaskDomain().mapFrom(creatableTask)

        assertEquals("some-name", mappedTask.name.name)
        assertEquals("some-description", mappedTask.description.description)
        assertEquals("some-attainability-description", mappedTask.attainabilityDescription.description)
        assertEquals("some-relevance-description", mappedTask.relevanceDescription.description)
        assertEquals(MeasurabilityDomain(), mappedTask.measurability)
        assertEquals(DueTime.NONE, mappedTask.dueTime)
        assertEquals("parent-task-id", mappedTask.parent.id)
        assertEquals("child-task-id-1", mappedTask.children[0].id)
        assertEquals("child-task-id-2", mappedTask.children[1].id)
    }

    @Test
    fun `updatable task mapping test`() {
        val creatableTask = UpdatableTask(
            id = "some-id",
            name = "some-name",
            description = "some-description",
            attainabilityDescription = "some-attainability-description",
            relevanceDescription = "some-relevance-description",
            measurability = null,
            dueTime = null,
            parent = "parent-task-id",
            children = listOf("child-task-id-1", "child-task-id-2")
        )

        val mappedTask = TaskDomain().mapFrom(creatableTask)

        assertEquals("some-id", mappedTask.id.id)
        assertEquals("some-name", mappedTask.name.name)
        assertEquals("some-description", mappedTask.description.description)
        assertEquals("some-attainability-description", mappedTask.attainabilityDescription.description)
        assertEquals("some-relevance-description", mappedTask.relevanceDescription.description)
        assertEquals(MeasurabilityDomain(), mappedTask.measurability)
        assertEquals(DueTime.NONE, mappedTask.dueTime)
        assertEquals("parent-task-id", mappedTask.parent.id)
        assertEquals("child-task-id-1", mappedTask.children[0].id)
        assertEquals("child-task-id-2", mappedTask.children[1].id)
    }

    @Test
    fun `due time mapping`() {
        val timeNow = Instant.now().toString()

        val mappedDueTime = DueTime().mapFrom(timeNow)

        assertEquals(mappedDueTime.dueTime, Instant.parse(timeNow))
    }

    @Test
    fun `measurability mapping`() {
        val measurability = Measurability(
            description = "some-measurability-description",
            progressMark = 49
        )

        val mappedMeasurability = MeasurabilityDomain().mapFrom(measurability)

        assertEquals("some-measurability-description", mappedMeasurability.description.description)
        assertEquals(49, mappedMeasurability.progress.percent)
    }

    @Test
    fun `search filter mapping`() {
        val searchFilter = SearchFilter(
            nameFilter = "name-filter",
            descriptionFilter = "description-filter",
            attainabilityDescriptionFilter = "attainability-filter",
            relevanceDescriptionFilter = "relevance-filter",
            measurabilityDescriptionFilter = "measurability-description-filter",
            progressMarkFilter = 35,
            progressMarkFilterEquality = EqualityMode.LESS_THAN,
            dueTimeFilter = null,
            dueTimeFilterEquality = EqualityMode.MORE_THAN,
            prentIdFilter = "parent-id-filter",
            childIdFilter = "child-id-filter"
        )

        val mappedSearchFilter = SearchFilterDomain().mapFrom(searchFilter)

        assertEquals("name-filter", mappedSearchFilter.nameFilter.name)
        assertEquals("description-filter", mappedSearchFilter.descriptionFilter.description)
        assertEquals("attainability-filter", mappedSearchFilter.attainabilityDescriptionFilter.description)
        assertEquals("relevance-filter", mappedSearchFilter.relevanceDescriptionFilter.description)
        assertEquals("measurability-description-filter", mappedSearchFilter.measurabilityDescriptionFilter.description)
        assertEquals(35, mappedSearchFilter.progressMarkFilter.percent)
        assertEquals(EqualityModeDomain.LESS_THAN, mappedSearchFilter.progressMarkFilterEquality)
        assertEquals(DueTime.NONE, mappedSearchFilter.dueTimeFilter)
        assertEquals(EqualityModeDomain.MORE_THAN, mappedSearchFilter.dueTimeFilterEquality)
        assertEquals("parent-id-filter", mappedSearchFilter.parentIdFilter.id)
        assertEquals("child-id-filter", mappedSearchFilter.childIdFilter.id)
    }
}