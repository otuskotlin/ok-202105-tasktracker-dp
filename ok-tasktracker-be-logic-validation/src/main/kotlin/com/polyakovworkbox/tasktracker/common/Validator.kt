package com.polyakovworkbox.tasktracker.common

interface Validator<T> {
    infix fun validate(data: T): ValidationResult
}