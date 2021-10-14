package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub
import com.polyakovworkbox.tasktracker.stubs.TaskStub
import com.polyakovworkbox.tasktracker.common.cor.handlers.CorChainDsl
import com.polyakovworkbox.tasktracker.common.cor.handlers.chain
import com.polyakovworkbox.tasktracker.common.cor.handlers.worker
import java.lang.IllegalArgumentException

internal fun CorChainDsl<BeContext>.taskCreateStub(title: String) = chain {
    this.title = title
    on {
        corStatus == CorStatus.RUNNING
                && debug.stub != Stub.NONE
    }
    worker {
        this.title = "SUCCESS stub case"
        on { debug.stub == Stub.SUCCESS }
        handle {
            responseTask = TaskStub.getModel()
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