package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import ru.otus.otuskotlin.marketplace.common.cor.handlers.CorChainDsl
import ru.otus.otuskotlin.marketplace.common.cor.handlers.worker

internal fun CorChainDsl<BeContext>.chainInit(title: String) =
    worker {
        this.title = title
        on { corStatus == CorStatus.NONE }
        handle {
            corStatus = CorStatus.RUNNING
        }
    }