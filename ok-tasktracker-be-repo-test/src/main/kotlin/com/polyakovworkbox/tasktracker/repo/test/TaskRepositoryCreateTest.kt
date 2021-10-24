package com.polyakovworkbox.tasktracker.repo.test

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskModelRequest
import kotlinx.coroutines.runBlocking

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

abstract class TaskRepositoryCreateTest {
    abstract val repo: ITaskRepo

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.create(TaskModelRequest(createObj)) }
        val expected = createObj.copy(id = result.result?.id ?: TaskId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected, result.result)
        assertNotEquals(TaskId.NONE, result.result?.id)
        assertEquals(emptyList<ApiError>(), result.errors)
    }

    companion object: BaseInitTasks() {

        private val createObj = Task(
            id = TaskId("00000000-0000-0000-0000-000000000000"),
            name = Name("create object"),
            description = Description("create object description")
        )
        override val initObjects: List<Task> = emptyList()
    }
}