package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub
import com.polyakovworkbox.tasktracker.common.handlers.CorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.chain
import com.polyakovworkbox.tasktracker.common.handlers.worker
import com.polyakovworkbox.tasktracker.stubs.TaskStub
import java.lang.IllegalArgumentException

internal fun CorChainDsl<BeContext>.taskUpdateStub(title: String) = chain {
    this.title = title
    on {
        corStatus == CorStatus.RUNNING
                && debug.stub != Stub.NONE
    }
    worker {
        this.title = "SUCCESS stub case"
        on { debug.stub == Stub.SUCCESS }
        handle {
            responseTask = TaskStub.getModelUpdated(this)
            corStatus = CorStatus.FINISHING
        }
    }
    worker {
        this.title = "ERROR_DB stub case"
        on { debug.stub == Stub.ERROR_DB }
        handle {
            errors.add(
                ApiError(
                    message = "DB error occurred"
                )
            )
            corStatus = CorStatus.FINISHING
        }
    }
    worker {
        this.title = "There is no such stub case as requested"
        on {
            corStatus == CorStatus.RUNNING
        }
        handle {
            corStatus = CorStatus.FAILING
            addError(
                e = IllegalArgumentException("No matching stub case for passed ${debug.stub}")
            )
        }
    }
}