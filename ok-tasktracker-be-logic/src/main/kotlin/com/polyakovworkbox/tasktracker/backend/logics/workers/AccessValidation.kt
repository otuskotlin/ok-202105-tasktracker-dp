package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.chain
import com.polyakovworkbox.tasktracker.common.handlers.worker
import jdk.jshell.Snippet

fun ICorChainDsl<BeContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Calculating access rights"
    on { corStatus == CorStatus.RUNNING }
    worker("Show tasks only to user that owns this task") {
        permitted = dbTask.ownerId.id == principal.id
    }
    worker {
        this.title = "Validating access rights"
        on { !permitted }
        handle {
            errors.add(
                ApiError(message = "User is not allowed to this operation")
            )
            corStatus = CorStatus.FAILING
        }
    }
}