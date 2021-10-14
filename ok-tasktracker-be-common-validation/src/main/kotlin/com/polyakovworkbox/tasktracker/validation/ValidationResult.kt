package com.polyakovworkbox.tasktracker.validation

class ValidationResult(val errors: List<ValidationError>) {
    val isSuccess: Boolean
        get() = errors.isEmpty()

    companion object {
        val SUCCESS = ValidationResult(emptyList())

    }
}
