package com.polyakovworkbox.tasktracker.validation.cor

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Mode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.cor.handlers.worker

fun ICorChainDsl<BeContext>.validation(block: ValidationBuilder<BeContext>.() -> Unit) {
    worker {
        title = "Validation"
        on { corStatus != CorStatus.FINISHING }
        handle {
            ValidationBuilder<BeContext>().apply {
                errorHandler {result ->
                    if(result.isSuccess) return@errorHandler

                    val allErrors = result.errors.map {
                        ApiError(message = it.message)
                    }

                    errors.addAll(allErrors)
                    corStatus = CorStatus.FAILING
                }
            }.apply(block).build().validate(this)
        }
    }
}


