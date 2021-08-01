package com.polyakovworkbox.tasktracker.backend.common.models

class Task (
        var messageType: String = "",
        var requestId: String = "",
        var debug: Debug = Debug.NONE,
        var task: UpdatableTask = UpdatableTask.NONE
) {
    companion object {
        val NONE = Task()
    }
}