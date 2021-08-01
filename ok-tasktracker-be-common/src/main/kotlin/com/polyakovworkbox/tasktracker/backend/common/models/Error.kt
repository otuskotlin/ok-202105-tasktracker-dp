package com.polyakovworkbox.tasktracker.backend.common.models

class Error(
    var field: String = "",
    var message: String = ""
) {
    companion object {
        val NONE = Error()
    }
}