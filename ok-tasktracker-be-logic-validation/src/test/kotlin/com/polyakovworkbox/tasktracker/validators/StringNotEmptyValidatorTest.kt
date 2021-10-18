package com.polyakovworkbox.tasktracker.validators

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class StringNotEmptyValidatorTest {

    @Test
    fun `string validation error`() {
        val data = ""

        val validator = StringNotEmptyValidator()
        val res = validator validate data

        assertFalse(res.isSuccess)
        assertNotNull(res.errors.find {it.message.contains("empty")})
    }

    @Test
    fun `string validation success`() {
        val data = "Not empty string"

        val validator = StringNotEmptyValidator()
        val res = validator validate data

        assertTrue(res.isSuccess)
        assertTrue(res.errors.isEmpty())

    }
}