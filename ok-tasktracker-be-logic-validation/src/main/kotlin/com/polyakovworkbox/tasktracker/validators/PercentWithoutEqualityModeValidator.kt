package com.polyakovworkbox.tasktracker.validators

import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter
import com.polyakovworkbox.tasktracker.common.ValidationFieldError
import com.polyakovworkbox.tasktracker.common.ValidationResult
import com.polyakovworkbox.tasktracker.common.Validator


class PercentWithoutEqualityModeValidator (
    val field: String = "Percent",
    val message: String = "EqualityMode is empty"
) : Validator<SearchFilter> {

    override fun validate(data: SearchFilter): ValidationResult {
        return if(data.progressMarkFilter != Percent.NONE && data.progressMarkFilterEquality == EqualityMode.NONE) {
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