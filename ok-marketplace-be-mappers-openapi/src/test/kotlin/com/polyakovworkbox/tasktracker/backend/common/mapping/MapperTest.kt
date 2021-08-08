package com.polyakovworkbox.tasktracker.backend.common.mapping

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MapperTest {

    @Test
    fun readTaskMappingTest() {
        val request = ReadTaskRequest(
            id = "some-id"
        )
        val context = BeContext()

        context.setQuery(request)

        assertEquals(TaskId("some-id"), context.requestTask.id)
    }

    @Test
    fun createTaskMappingTest() {
        val requestDemand = CreateTaskRequest(
                createTask = CreatableTask(
                        name = "some name",
                        description = "some description"
                )
        )

        val context = BeContext()

        context.setQuery(requestDemand)

        assertEquals(Name("some name"), context.requestTask.name)
        assertEquals(Description("some description"), context.requestTask.description)

    }
}