package com.polyakovworkbox.tasktracker.validators

import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PercentWithoutEqualityModeValidatorTest {

    @Test
    fun `progress filter is filled along with equality mode`() {
        val data = SearchFilter(
            progressMarkFilter = Percent(100),
            progressMarkFilterEquality = EqualityMode.EQUALS
        )

        val validator = PercentWithoutEqualityModeValidator()
        val res = validator validate data

        assertTrue(res.isSuccess)
        assertTrue(res.errors.isEmpty())
    }

    @Test
    fun `progress filter is filled without equality mode`() {
        val data = SearchFilter(
            progressMarkFilter = Percent(100),
        )

        val validator = PercentWithoutEqualityModeValidator()
        val res = validator validate data

        assertFalse(res.isSuccess)
        assertNotNull(res.errors.find {it.message == "EqualityMode is empty"})
    }
}