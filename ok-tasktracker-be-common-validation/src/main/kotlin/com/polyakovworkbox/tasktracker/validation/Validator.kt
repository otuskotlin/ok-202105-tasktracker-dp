package com.polyakovworkbox.tasktracker.validation

interface Validator<T> {
    infix fun validate(data: T): ValidationResult
}