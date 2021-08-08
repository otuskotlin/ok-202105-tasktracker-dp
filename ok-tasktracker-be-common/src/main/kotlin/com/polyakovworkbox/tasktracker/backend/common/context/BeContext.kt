package com.polyakovworkbox.tasktracker.backend.common.context

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.Debug
import com.polyakovworkbox.tasktracker.backend.common.models.general.RequestId
import com.polyakovworkbox.tasktracker.backend.common.models.general.ResponseId
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter

data class BeContext(
    var requestId: RequestId = RequestId.NONE,
    var debug: Debug = Debug.DEFAULT,

    var requestTask: Task = Task.NONE,
    var requestTaskId: TaskId = TaskId.NONE,
    var searchFilter: SearchFilter = SearchFilter.ALL,
    var responseTask: Task = Task.NONE,
    var responseTasks: MutableList<Task> = mutableListOf(),

    var responseId: ResponseId = ResponseId.NONE,
    var errors: MutableList<ApiError> = mutableListOf()
) {
}