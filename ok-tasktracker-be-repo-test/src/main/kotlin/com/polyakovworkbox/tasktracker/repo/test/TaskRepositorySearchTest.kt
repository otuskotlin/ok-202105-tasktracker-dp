package com.polyakovworkbox.tasktracker.repo.test

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskFilterRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

abstract class TaskRepositorySearchTest {
    abstract val repo: ITaskRepo

    @Test
    fun searchOwner() {
        val result = runBlocking { repo.search(TaskFilterRequest()) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[1], initObjects[3])
        assertEquals(expected.sortedBy { it.id.asString() }, result.result.sortedBy { it.id.asString() })
        assertEquals(emptyList<ApiError>(), result.errors)
    }

    @Test
    fun searchDealSide() {
        val result = runBlocking { repo.search(TaskFilterRequest()) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[2], initObjects[4])
        assertEquals(expected.sortedBy { it.id.asString() }, result.result.sortedBy { it.id.asString() })
        assertEquals(emptyList<ApiError>(), result.errors)
    }

    companion object: BaseInitTasks() {

        override val initObjects: List<Task> = listOf(
            createInitTestModel("someTaskForSearch"),
        )
    }
}