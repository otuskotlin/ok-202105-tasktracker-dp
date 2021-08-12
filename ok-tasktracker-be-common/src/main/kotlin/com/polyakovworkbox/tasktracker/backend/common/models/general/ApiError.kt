package com.polyakovworkbox.tasktracker.backend.common.models.general

data class ApiError(
    var field: String = "",
    var message: String = ""
) {
    companion object {
        val NONE = ApiError()
    }
}