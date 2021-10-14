package com.polyakovworkbox.tasktracker.validation.cor

import com.polyakovworkbox.tasktracker.validation.lib.ValidationResult
import com.polyakovworkbox.tasktracker.validation.lib.Validator

class DefaultValidationOperation<C, T> (
    val onBlock: C.() -> T,
    val errorHandler: C.(ValidationResult) -> Unit,
    val validator: Validator<T>
) : ValidationOperation<C, T> {

    override fun validate(context: C) {
        val value = context.onBlock()
        val res = validator.validate(value)
        context.errorHandler(res)
    }
}