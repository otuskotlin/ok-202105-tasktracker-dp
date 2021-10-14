package com.polyakovworkbox.tasktracker.validation.lib

interface Validator<T> {
    infix fun validate(data: T): ValidationResult
}