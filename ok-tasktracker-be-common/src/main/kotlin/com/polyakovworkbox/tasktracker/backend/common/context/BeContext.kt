package com.polyakovworkbox.tasktracker.backend.common.context

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Debug
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.general.RequestId
import com.polyakovworkbox.tasktracker.backend.common.models.general.ResponseId
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import java.time.Instant

data class BeContext(
    var startTime: Instant = Instant.MIN,
    var operation: Operation = Operation.NONE,

    var requestId: RequestId = RequestId.NONE,
    var debug: Debug = Debug.DEFAULT,

    var config: ContextConfig = ContextConfig(),
    var taskRepo: ITaskRepo = ITaskRepo.NONE,

    var requestTask: Task = Task(),
    var requestTaskId: TaskId = TaskId.NONE,
    var status: ResponseStatus = ResponseStatus.SUCCESS,
    var searchFilter: SearchFilter = SearchFilter(),
    var responseTask: Task = Task(),
    var responseTasks: MutableList<Task> = mutableListOf(),

    var corStatus: CorStatus = CorStatus.NONE,

    var responseId: ResponseId = ResponseId.NONE,
    var errors: MutableList<ApiError> = mutableListOf(),

) {
    fun addError(e: Throwable, isErrorStatus: Boolean = true) {
        errors.add(ApiError(e))
        if(isErrorStatus) status = ResponseStatus.ERROR
    }
}