package com.polyakovworkbox.tasktracker.validation.lib.validators

import com.polyakovworkbox.tasktracker.validation.lib.ValidationFieldError
import com.polyakovworkbox.tasktracker.validation.lib.ValidationResult
import com.polyakovworkbox.tasktracker.validation.lib.Validator

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