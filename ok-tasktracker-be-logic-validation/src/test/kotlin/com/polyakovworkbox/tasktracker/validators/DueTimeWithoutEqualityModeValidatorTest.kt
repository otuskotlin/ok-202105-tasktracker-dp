package com.polyakovworkbox.tasktracker.validators

import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter
import org.junit.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DueTimeWithoutEqualityModeValidatorTest {

    @Test
    fun `due time filter is filled along with equality mode`() {
        val data = SearchFilter(
            dueTimeFilter = DueTime(Instant.now()),
            dueTimeFilterEquality = EqualityMode.EQUALS
        )

        val validator = DueTimeWithoutEqualityModeValidator()
        val res = validator validate data

        assertTrue(res.isSuccess)
        assertTrue(res.errors.isEmpty())
    }

    @Test
    fun `due time filter is filled without equality mode`() {
        val data = SearchFilter(
            dueTimeFilter = DueTime(
                LocalDateTime.of(2050, Month.JANUARY, 1, 12, 0,0)
                    .atZone(ZoneId.of("UTC+03:00"))
                    .toInstant()
            )
        )

        val validator = DueTimeWithoutEqualityModeValidator()
        val res = validator validate data

        assertFalse(res.isSuccess)
        assertNotNull(res.errors.find {it.message == "EqualityMode is empty"})
    }
}