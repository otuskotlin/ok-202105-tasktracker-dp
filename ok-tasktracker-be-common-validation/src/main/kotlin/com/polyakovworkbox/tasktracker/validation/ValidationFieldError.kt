package com.polyakovworkbox.tasktracker.validation

class ValidationFieldError(
    override val message: String,
    val field: String
) : ValidationError {

}