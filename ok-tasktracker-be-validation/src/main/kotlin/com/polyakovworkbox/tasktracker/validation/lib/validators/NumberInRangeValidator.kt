package com.polyakovworkbox.tasktracker.validation.lib.validators

import com.polyakovworkbox.tasktracker.validation.lib.ValidationFieldError
import com.polyakovworkbox.tasktracker.validation.lib.ValidationResult
import com.polyakovworkbox.tasktracker.validation.lib.Validator

class NumberInRangeValidator<T: Comparable<T>> (
    private val field: String,
    private val message: String = "Number is not in range",
    private val min: T,
    private val max: T
) : Validator<T>{
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