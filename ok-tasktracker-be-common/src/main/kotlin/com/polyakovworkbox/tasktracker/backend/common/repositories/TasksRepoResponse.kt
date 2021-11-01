package com.polyakovworkbox.tasktracker.backend.common.repositories

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task

data class TasksRepoResponse (
    override val result: List<Task>,
    override val isSuccess: Boolean,
    override val errors: List<ApiError> = emptyList()
) : ITaskRepoResponse<List<Task>> {
    constructor(result: List<Task>) : this(result, true)
    constructor(error: ApiError) : this(emptyList(), false, listOf(error))
}