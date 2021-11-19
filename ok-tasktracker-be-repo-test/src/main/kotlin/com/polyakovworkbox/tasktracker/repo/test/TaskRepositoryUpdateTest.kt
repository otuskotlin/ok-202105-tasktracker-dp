package com.polyakovworkbox.tasktracker.repo.test

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.OwnerId
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskModelRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

abstract class TaskRepositoryUpdateTest {
    abstract val repo: ITaskRepo

    @Test
    fun updateSuccess() {
        val result = runBlocking { repo.update(TaskModelRequest(updateObj)) }
        assertEquals(true, result.isSuccess)
        assertEquals(updateObj, result.result)
        assertEquals(emptyList<ApiError>(), result.errors)
    }

    @Test
    fun updateNotFound() {
        val result = runBlocking { repo.update(TaskModelRequest(updateObjNotFound)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(listOf(ApiError(field = "id", message = "Not Found")), result.errors)
    }

    companion object: BaseInitTasks() {
        override val initObjects: List<Task> = listOf(
            createInitTestModel("update")
        )
        private val updateId = initObjects.first().id
        private val updateIdNotFound = TaskId.getRandom()

        private val updateObj = Task(
            id = updateId,
            ownerId = OwnerId("00560000-0000-0000-0000-000000000001"),
            name = Name("update object"),
            description = Description("update object description"),
        )

        private val updateObjNotFound = Task(
            id = updateIdNotFound,
            ownerId = OwnerId("00560000-0000-0000-0000-000000000001"),
            name = Name("update object not found"),
            description = Description("update object not found description"),
        )
    }
}