package com.polyakovworkbox.tasktracker.validation.cor

import com.polyakovworkbox.tasktracker.validation.lib.ValidationResult
import com.polyakovworkbox.tasktracker.validation.lib.Validator

class ValidateOperationBuilder<C, T> (
    private val errorHandler: C.(ValidationResult) -> Unit = {}
) {
    private lateinit var onBlock: C.() -> T
    private lateinit var validator: Validator<T>

    fun validator(validator: Validator<T>) {
        this.validator = validator
    }

    fun on(block: C.() -> T) {
        onBlock = block
    }

    fun build(): ValidationOperation<C, T> {
        return DefaultValidationOperation(
            onBlock = onBlock,
            errorHandler = errorHandler,
            validator = validator
        )
    }
}
