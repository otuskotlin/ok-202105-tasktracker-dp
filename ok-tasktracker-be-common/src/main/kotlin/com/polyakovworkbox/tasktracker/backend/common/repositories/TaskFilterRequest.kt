package com.polyakovworkbox.tasktracker.backend.common.repositories

data class TaskFilterRequest(
    val searchString: String = ""
): ITaskRepoRequest