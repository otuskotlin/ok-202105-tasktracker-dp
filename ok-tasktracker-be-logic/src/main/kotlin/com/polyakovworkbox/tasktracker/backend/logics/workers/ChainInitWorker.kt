package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.common.handlers.CorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.worker

internal fun CorChainDsl<BeContext>.chainInit(title: String) =
    worker {
        this.title = title
        on { corStatus == CorStatus.NONE }
        handle {
            corStatus = CorStatus.RUNNING
        }
    }