package com.polyakovworkbox.tasktracker.backend.logics

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Debug
import com.polyakovworkbox.tasktracker.backend.common.models.general.Mode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.stubs.TaskStub
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TaskCrudValidationTest {

    @Test
    fun `create validation empty id test - validation ok`() {
        val crud = TaskCrud()
        val context = BeContext(
            requestTask = TaskStub.getModel().apply { id = TaskId("1") },
            debug = Debug(
                mode = Mode.STUB,
                stub = Stub.SUCCESS
            ),
            operation = Operation.CREATE
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.corStatus)
        assertTrue(context.errors.isEmpty())
    }

    @Test
    fun `create validation empty id test - validation failed`() {
        val crud = TaskCrud()
        val context = BeContext(
            requestTask = TaskStub.getModel().apply { id = TaskId.NONE },
            debug = Debug(
                mode = Mode.PROD,
            ),
            operation = Operation.CREATE
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.ERROR, context.corStatus)
        assertNotNull(context.errors.find { it.message == "String is empty" })
    }
}