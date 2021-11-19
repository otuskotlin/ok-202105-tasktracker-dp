package com.polyakovworkbox.tasktracker.repo.test

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskIdRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

abstract class TaskRepositoryReadTest {
    abstract val repo: ITaskRepo

    @Test
    fun readSuccess() {
        val first = initObjects.firstOrNull()

        val result = runBlocking { repo.read(
            if(first == null)
                TaskIdRequest.getRandom()
            else
                TaskIdRequest(first.id.asUUID())
        )
        }

        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.result)
        assertEquals(emptyList<ApiError>(), result.errors)
    }

    @Test
    fun readNotFound() {
        val result = runBlocking { repo.read(TaskIdRequest.getRandom()) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(
            listOf(ApiError(field = "id", message = "Not Found")),
            result.errors
        )
    }

    companion object: BaseInitTasks() {
        override val initObjects: List<Task> = listOf(
            createInitTestModel("read")
        )

        private val readSuccessStub = initObjects.first()
        val successId = readSuccessStub.id
        val notFoundId = TaskId.getRandom()

    }
}