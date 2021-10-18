package com.polyakovworkbox.tasktracker.common

class ValidationFieldError(
    override val message: String,
    val field: String
) : ValidationError {

}