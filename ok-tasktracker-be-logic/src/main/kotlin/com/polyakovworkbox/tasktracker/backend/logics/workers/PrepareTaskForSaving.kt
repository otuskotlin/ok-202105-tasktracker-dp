package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.worker

fun ICorChainDsl<BeContext>.prepareTaskForSaving(title: String) {
    worker {
        this.title = title
        description = title
        on { corStatus == CorStatus.RUNNING }
        handle {
            with(dbTask) {
                this.name = requestTask.name
                description = requestTask.description
            }
        }
    }
}