package com.polyakovworkbox.tasktracker.backend.common.models.general

@JvmInline
value class Percent(
    val percent: Int = 0
) {
    companion object {
        val NONE = Percent()
    }
}