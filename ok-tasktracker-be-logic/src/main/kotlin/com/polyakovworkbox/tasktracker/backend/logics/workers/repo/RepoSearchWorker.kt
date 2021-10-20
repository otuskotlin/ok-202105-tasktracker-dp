package com.polyakovworkbox.tasktracker.backend.logics.workers.repo

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskFilterRequest
import com.polyakovworkbox.tasktracker.common.handlers.CorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.worker

internal fun CorChainDsl<BeContext>.repoSearch(title: String) = worker {
    this.title = title

    on { corStatus == CorStatus.RUNNING }

    handle {
        val result = taskRepo.search(TaskFilterRequest(searchFilter.toString()))
        if (result.isSuccess) {
            responseTasks = result.result.toMutableList()
        } else {
            errors.addAll(result.errors)
        }
    }
}