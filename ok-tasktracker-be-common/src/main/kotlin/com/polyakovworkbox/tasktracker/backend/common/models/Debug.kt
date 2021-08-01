package com.polyakovworkbox.tasktracker.backend.common.models

data class Debug(
    private val mode: String = "",
    private val stub: String = ""
) {
    companion object {
        val NONE = Debug()
    }
}
