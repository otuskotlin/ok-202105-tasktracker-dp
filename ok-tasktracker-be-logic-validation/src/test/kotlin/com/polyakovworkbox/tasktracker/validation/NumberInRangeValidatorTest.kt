package com.polyakovworkbox.tasktracker.validation

import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability
import com.polyakovworkbox.tasktracker.validation.lib.validators.NumberInRangeValidator
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NumberInRangeValidatorTest {

    @Test
    fun `progress has correct value`() {
        val data = Measurability(
            progress = Percent(100)
        )

        val validator = NumberInRangeValidator(
            field = "Measurability",
            min = 0,
            max = 100
        )
        val res = validator validate data.progress.percent

        assertTrue(res.isSuccess)
        assertTrue(res.errors.isEmpty())
    }

    @Test
    fun `progress value is out of bounds`() {
        val data = Measurability(
            progress = Percent(101)
        )

        val validator = NumberInRangeValidator(
            field = "Measurability",
            min = 0,
            max = 100
        )
        val res = validator validate data.progress.percent

        assertFalse(res.isSuccess)
        assertNotNull(res.errors.find {it.message.contains("is not in range")})
    }
}