package com.polyakovworkbox.tasktracker.backend.common.models.general

@JvmInline
value class Percent(
    val percent: Int = Int.MIN_VALUE
) {
    companion object {
        val NONE = Percent()
    }
}