package com.polyakovworkbox.tasktracker.validators

import com.polyakovworkbox.tasktracker.common.ValidationFieldError
import com.polyakovworkbox.tasktracker.common.ValidationResult
import com.polyakovworkbox.tasktracker.common.Validator


class NumberInRangeValidator<T: Comparable<T>> (
    private val field: String,
    private val message: String = "Number is not in range",
    private val min: T,
    private val max: T
) : Validator<T> {
    override fun validate(data: T): ValidationResult {
        return if (data in min..max) {
            ValidationResult.SUCCESS
        } else {
            ValidationResult(
                listOf(
                    ValidationFieldError(
                        field = field,
                        message = message
                    )
                )
            )
        }
    }
}