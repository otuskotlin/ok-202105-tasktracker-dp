package com.polyakovworkbox.tasktracker.validation.lib.validators

import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter
import com.polyakovworkbox.tasktracker.validation.ValidationFieldError
import com.polyakovworkbox.tasktracker.validation.ValidationResult
import com.polyakovworkbox.tasktracker.validation.Validator


class DueTimeWithoutEqualityModeValidator (
    val field: String = "Due time",
    val message: String = "EqualityMode is empty"
) : Validator<SearchFilter> {

    override fun validate(data: SearchFilter): ValidationResult {
        return if(data.dueTimeFilter != DueTime.NONE && data.dueTimeFilterEquality == EqualityMode.NONE) {
            ValidationResult(listOf(
                ValidationFieldError(
                    message = message,
                    field = field
                )
            ))
        } else {
            ValidationResult.SUCCESS
        }
    }
}