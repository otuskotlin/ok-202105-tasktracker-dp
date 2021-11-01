package com.polyakovworkbox.tasktracker.repo.test

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskFilterRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

abstract class TaskRepositorySearchTest {
    abstract val repo: ITaskRepo

    @Test
    fun searchFound() {
        val result = runBlocking { repo.search(TaskFilterRequest(progressMarkFilter = 50)) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[0], initObjects[2])
        assertEquals(expected.sortedBy { it.id.asString() }, result.result.sortedBy { it.id.asString() })
        assertEquals(emptyList<ApiError>(), result.errors)
    }

    @Test
    fun searchNotFound() {
        val result = runBlocking { repo.search(TaskFilterRequest(nameFilter = "Non-existent task")) }
        assertEquals(true, result.isSuccess)
        val expected = emptyList<Task>()
        assertEquals(expected.sortedBy { it.id.asString() }, result.result.sortedBy { it.id.asString() })
        assertEquals(emptyList<ApiError>(), result.errors)
    }

    companion object: BaseInitTasks() {

        override val initObjects: List<Task> = listOf(
            createHalfDoneTestModel("halfDoneTask1"),
            createInitTestModel("someTaskForSearch"),
            createHalfDoneTestModel("halfDoneTask2"),
        )
    }
}