package com.polyakovworkbox.tasktracker.backend.common.models.general

data class ApiError(
    var field: String = "",
    var message: String = ""
) {
    constructor(e: Throwable): this(
        message = e.message ?: ""
    )

    companion object {
        val NONE = ApiError()
    }
}