package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import ru.otus.otuskotlin.marketplace.common.cor.handlers.CorChainDsl
import ru.otus.otuskotlin.marketplace.common.cor.handlers.worker
import java.lang.IllegalStateException

internal fun CorChainDsl<BeContext>.checkOperation(title: String, targetOperation: Operation) =
    worker {
        this.title = title
        description = "Check that operation is the same as the passed one"
        on { operation != targetOperation }
        handle {
            corStatus = CorStatus.FAILING
            addError(
                e = IllegalArgumentException("Unexpected target operation: $targetOperation has been called for $operation")
            )
        }
    }