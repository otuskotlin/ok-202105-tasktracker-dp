package com.polyakovworkbox.tasktracker.validation.lib

class ValidationFieldError(
    override val message: String,
    val field: String
) : ValidationError {

}