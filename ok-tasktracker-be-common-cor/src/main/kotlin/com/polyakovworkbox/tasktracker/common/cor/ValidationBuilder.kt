package com.polyakovworkbox.tasktracker.common.cor

import com.polyakovworkbox.tasktracker.common.ValidationResult

class ValidationBuilder<C> {
    var errorHandler: C.(ValidationResult) -> Unit = {}
    val validators = mutableListOf<ValidationOperation<C, *>>()

    fun errorHandler(block: C.(ValidationResult) -> Unit) {
        errorHandler = block
    }

    fun <T> validate(block: ValidateOperationBuilder<C, T>.() -> Unit) {
        val validator = ValidateOperationBuilder<C, T>(errorHandler).apply(block).build()
        validators.add(validator)
    }

    fun build(): ValidationOperationAll<C> {
        return ValidationOperationAll(validators)
    }
}

