package com.polyakovworkbox.tasktracker.validators

import com.polyakovworkbox.tasktracker.common.ValidationFieldError
import com.polyakovworkbox.tasktracker.common.ValidationResult
import com.polyakovworkbox.tasktracker.common.Validator


class StringNotEmptyValidator(
    val field: String = "",
    val message: String = "String is empty"
) : Validator<String> {

    override fun validate(data: String): ValidationResult {
        return if(data.isBlank()) {
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