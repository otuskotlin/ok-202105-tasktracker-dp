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

abstract class TaskRepositoryDeleteTest {
    abstract val repo: ITaskRepo

    @Test
    fun deleteSuccess() {
        val result = runBlocking { repo.delete(TaskIdRequest(initObjects.firstOrNull()?.id?.asUUID() ?: UUID.randomUUID())) }

        assertEquals(true, result.isSuccess)
        assertEquals(deleteSuccessStub, result.result)
        assertEquals(emptyList<ApiError>(), result.errors)
    }

    @Test
    fun deleteNotFound() {
        val result = runBlocking { repo.read(TaskIdRequest(UUID.randomUUID())) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(
            listOf(ApiError(field = "id", message = "Not Found")),
            result.errors
        )
    }

    companion object: BaseInitTasks() {
        override val initObjects: List<Task> = listOf(
            createInitTestModel("delete")
        )
        private val deleteSuccessStub = initObjects.first()
        val successId = deleteSuccessStub.id
        val notFoundId = TaskId(UUID.randomUUID())
    }
}