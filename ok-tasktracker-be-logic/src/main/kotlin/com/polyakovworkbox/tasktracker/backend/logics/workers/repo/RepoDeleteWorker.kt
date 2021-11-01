package com.polyakovworkbox.tasktracker.backend.logics.workers.repo

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskIdRequest
import com.polyakovworkbox.tasktracker.common.handlers.CorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.worker

internal fun CorChainDsl<BeContext>.repoDelete(title: String) = worker {
    this.title = title

    on { corStatus == CorStatus.RUNNING }

    handle {
        val result = taskRepo.delete(TaskIdRequest(id = requestTaskId.id))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            responseTask =  resultValue
        } else {
            errors.addAll(result.errors)
        }
    }
}