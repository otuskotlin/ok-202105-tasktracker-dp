package com.polyakovworkbox.tasktracker.backend.common.repositories

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task

data class TaskRepoResponse (
    override val result: Task?,
    override val isSuccess: Boolean,
    override val errors: List<ApiError> = emptyList()
) : ITaskRepoResponse<Task?> {
    constructor(result: Task) : this(result, true)
    constructor(error: ApiError) : this(null, false, listOf(error))
}