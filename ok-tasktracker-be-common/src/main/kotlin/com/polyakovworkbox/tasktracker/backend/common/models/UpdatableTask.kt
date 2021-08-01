package com.polyakovworkbox.tasktracker.backend.common.models

class UpdatableTask(
    var name: String = "",
    var description: String = "",
    var id: String = ""
) {
    companion object {
        val NONE = UpdatableTask()
    }
}
