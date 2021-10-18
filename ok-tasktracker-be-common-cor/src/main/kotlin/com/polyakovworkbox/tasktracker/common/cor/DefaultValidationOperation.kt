package com.polyakovworkbox.tasktracker.common.cor

import com.polyakovworkbox.tasktracker.common.ValidationResult
import com.polyakovworkbox.tasktracker.common.Validator

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