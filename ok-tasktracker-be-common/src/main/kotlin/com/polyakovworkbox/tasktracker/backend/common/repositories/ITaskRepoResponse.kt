package com.polyakovworkbox.tasktracker.backend.common.repositories

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError

interface ITaskRepoResponse<T> {
    val isSuccess: Boolean
    val errors: List<ApiError>
    val result: T
}