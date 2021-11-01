package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Mode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Stub
import com.polyakovworkbox.tasktracker.common.handlers.CorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.worker

internal fun CorChainDsl<BeContext>.selectDB(title: String) = worker {
    this.title = title

    handle {
        taskRepo = when (debug.mode) {
            Mode.PROD -> config.repoProd
            Mode.TEST -> config.repoTest
            Mode.STUB -> {
                if (debug.stub == Stub.NONE) {
                    debug.stub = Stub.SUCCESS
                }
                config.repoTest
            }
        }
    }
}