package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import ru.otus.otuskotlin.marketplace.common.cor.handlers.CorChainDsl
import ru.otus.otuskotlin.marketplace.common.cor.handlers.chain
import ru.otus.otuskotlin.marketplace.common.cor.handlers.worker

internal fun CorChainDsl<BeContext>.prepareResponse(title: String) = chain {
    this.title = title
    worker {
        this.title = "Happy path"
        on { corStatus in setOf(CorStatus.RUNNING, CorStatus.FINISHING)}
        handle {
            corStatus = CorStatus.SUCCESS
        }
    }
    worker {
        this.title = "Something went wrong"
        on { corStatus != CorStatus.SUCCESS }
        handle {
            corStatus = CorStatus.ERROR
        }
    }

}