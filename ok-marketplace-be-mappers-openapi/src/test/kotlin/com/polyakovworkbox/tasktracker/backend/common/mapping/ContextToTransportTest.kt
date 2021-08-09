package com.polyakovworkbox.tasktracker.backend.common.mapping

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ResponseResult
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.general.ResponseId
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability as MeasurabilityDomain
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ContextToTransportTest {

    @Test
    fun `response with errors mapping`() {
        val context = BeContext().apply {
            responseId = ResponseId("responseId")
            responseTask = Task()
            errors = mutableListOf(
                ApiError("field-id-1", "error-message-1"),
                ApiError("field-id-2", "error-message-2")
            )
        }

        val createResponse = context.toCreateResponse()

        assertEquals("responseId", createResponse.responseId)
        assertEquals(null, createResponse.task)
        assertEquals("field-id-1", createResponse.errors?.get(0)?.field)
        assertEquals("error-message-1", createResponse.errors?.get(0)?.message)
        assertEquals("field-id-2", createResponse.errors?.get(1)?.field)
        assertEquals("error-message-2", createResponse.errors?.get(1)?.message)
    }

    @Test
    fun `create response mapping`() {
        val context = BeContext().apply {
            responseId = ResponseId("responseId")
            responseTask = Task()
            errors = mutableListOf()
        }

        val createResponse = context.toCreateResponse()

        assertEquals("responseId", createResponse.responseId)
        assertEquals(emptyList(), createResponse.errors)
        assertEquals("CreateTaskResponse", createResponse.messageType)
        assertEquals(ResponseResult.SUCCESS, createResponse.result)
    }

    @Test
    fun `read response mapping`() {
        val context = BeContext().apply {
            responseId = ResponseId("responseId")
            responseTask = Task()
            errors = mutableListOf()
        }

        val createResponse = context.toReadResponse()

        assertEquals("responseId", createResponse.responseId)
        assertEquals(emptyList(), createResponse.errors)
        assertEquals("ReadTaskResponse", createResponse.messageType)
        assertEquals(ResponseResult.SUCCESS, createResponse.result)
    }

    @Test
    fun `update response mapping`() {
        val context = BeContext().apply {
            responseId = ResponseId("responseId")
            responseTask = Task()
            errors = mutableListOf()
        }

        val createResponse = context.toUpdateResponse()

        assertEquals("responseId", createResponse.responseId)
        assertEquals(emptyList(), createResponse.errors)
        assertEquals("UpdateTaskResponse", createResponse.messageType)
        assertEquals(ResponseResult.SUCCESS, createResponse.result)
    }

    @Test
    fun `delete response mapping`() {
        val context = BeContext().apply {
            responseId = ResponseId("responseId")
            responseTask = Task()
            errors = mutableListOf()
        }

        val createResponse = context.toDeleteResponse()

        assertEquals("responseId", createResponse.responseId)
        assertEquals(emptyList(), createResponse.errors)
        assertEquals("DeleteTaskResponse", createResponse.messageType)
        assertEquals(ResponseResult.SUCCESS, createResponse.result)
    }

    @Test
    fun `search response mapping`() {
        val context = BeContext().apply {
            responseId = ResponseId("responseId")
            responseTasks = mutableListOf()
            errors = mutableListOf()
        }

        val createResponse = context.toSearchResponse()

        assertEquals("responseId", createResponse.responseId)
        assertEquals(emptyList(), createResponse.errors)
        assertEquals("SearchTasksResponse", createResponse.messageType)
        assertEquals(ResponseResult.SUCCESS, createResponse.result)
    }

    @Test
    fun `map task`() {
        val currentDateTime = LocalDateTime.now()
        val task = Task(
            id = TaskId("task-id"),
            name = Name("task-name"),
            description = Description("task-description"),
            attainabilityDescription = Description("task-attainability-description"),
            relevanceDescription = Description("task-relevance-description"),
            measurability = MeasurabilityDomain(Description("task-measurability-description"), Percent(0)),
            dueTime = DueTime(currentDateTime),
            parent = TaskIdReference("parent-id"),
            children = listOf(TaskIdReference("child-id-1"), TaskIdReference("child-id-2"))
        )

        val taskMapped = task.mapToTransport()

        assertEquals("task-id", taskMapped.id)
        assertEquals("task-name", taskMapped.name)
        assertEquals("task-description", taskMapped.description)
        assertEquals("task-attainability-description", taskMapped.attainabilityDescription)
        assertEquals("task-relevance-description", taskMapped.relevanceDescription)
        assertEquals("task-measurability-description", taskMapped.measurability?.description)
        assertEquals(0, taskMapped.measurability?.progressMark)
        assertEquals(currentDateTime.toString(), taskMapped.dueTime)
        assertEquals("parent-id", taskMapped.parent)
        assertEquals("child-id-1", taskMapped.children?.get(0))
        assertEquals("child-id-2", taskMapped.children?.get(1))
    }

}