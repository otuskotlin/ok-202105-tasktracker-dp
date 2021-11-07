package com.polyakovworkbox.tasktracker.backend.logics

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.context.ResponseStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Debug
import com.polyakovworkbox.tasktracker.backend.common.models.general.Mode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub
import com.polyakovworkbox.tasktracker.stubs.TaskStub
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class TaskCrudTest {

    @Test
    fun taskCreateSuccess() {
        val crud = TaskCrud()
        val context = BeContext(
            requestTask = TaskStub.getModel(),
            debug = Debug(
                mode = Mode.STUB,
                stub = Stub.SUCCESS
            ),
            operation = Operation.CREATE
        )

        runBlocking {
            crud.create(context)

            val expected = TaskStub.getModel()
            assertEquals(ResponseStatus.SUCCESS, context.status)
            with(context.responseTask) {
                assertEquals(expected.id, id)
                assertEquals(expected.name, name)
                assertEquals(expected.description, description)
            }
        }

    }
}