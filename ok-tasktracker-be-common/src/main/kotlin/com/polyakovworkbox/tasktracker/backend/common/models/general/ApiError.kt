package com.polyakovworkbox.tasktracker.backend.common.models.general

data class ApiError(
    var field: String = "",
    var message: String = "",
    var error: Throwable = NoneException
) {
    constructor(e: Throwable): this(
        message = e.message ?: "",
        error = e
    )

    companion object {
        val NONE = ApiError()
    }
}

object NoneException : Throwable()