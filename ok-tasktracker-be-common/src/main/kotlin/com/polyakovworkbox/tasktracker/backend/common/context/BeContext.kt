package com.polyakovworkbox.tasktracker.backend.common.context

import com.polyakovworkbox.tasktracker.backend.common.models.task.Task

data class BeContext(
    var requestId: String = "",
    var requestTask: Task = Task.NONE,
    var responseTask: Task = Task.NONE,
    var responseTasks: MutableList<Task> = mutableListOf(),
    var errors: MutableList<Error> = mutableListOf()
) {
}