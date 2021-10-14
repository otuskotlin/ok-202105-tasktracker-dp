package com.polyakovworkbox.tasktracker.validation

import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.validation.lib.validators.AtLeastOneTaskValueProvidedValidator
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AtLeastOneTaskValueProvidedValidatorTest {

    @Test
    fun `task has at least one field filled`() {
        val data = Task(
            name = Name("Some name")
        )

        val validator = AtLeastOneTaskValueProvidedValidator()
        val res = validator validate data

        assertTrue(res.isSuccess)
        assertTrue(res.errors.isEmpty())
    }

    @Test
    fun `task has no field filled`() {
        val data = Task()

        val validator = AtLeastOneTaskValueProvidedValidator()
        val res = validator validate data

        assertFalse(res.isSuccess)
        assertNotNull(res.errors.find {it.message == "One of the task values should be provided"})
    }
}