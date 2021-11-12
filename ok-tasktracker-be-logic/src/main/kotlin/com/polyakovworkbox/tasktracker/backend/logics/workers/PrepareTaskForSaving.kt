package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.worker

fun ICorChainDsl<BeContext>.prepareTaskForSaving(title: String) {
    worker {
        this.title = title
        description = title
        on { corStatus == CorStatus.RUNNING }
        handle {
            with(dbTask) {
                id = requestTask.id
                ownerId = requestTask.ownerId
                name = requestTask.name
                description = requestTask.description
                dueTime = requestTask.dueTime
                attainabilityDescription = requestTask.attainabilityDescription
                relevanceDescription = requestTask.relevanceDescription
                measurability = Measurability(
                    requestTask.measurability.description,
                    requestTask.measurability.progress
                )
                parent = requestTask.parent
                children = requestTask.children
            }
        }
    }
}