package com.polyakovworkbox.tasktracker.backend.common.repositories

import com.polyakovworkbox.tasktracker.backend.common.models.task.Task

data class TaskModelRequest(
    val task: Task = Task()
): ITaskRepoRequest