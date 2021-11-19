package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.permissions.FrontendPermissions
import com.polyakovworkbox.tasktracker.backend.common.permissions.UserGroups
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.chain
import com.polyakovworkbox.tasktracker.common.handlers.worker

fun ICorChainDsl<BeContext>.frontendPermissions(title: String) = chain {
    this.title = title
    description = "Calculating frontend permissions"

    on { corStatus == CorStatus.RUNNING }

    worker {
        this.title = "User permissions"
        description = this.title
        on { responseTask.ownerId.id == principal.id }
        handle {
            val permAdd: Set<FrontendPermissions> = principal.groups.map {
                when (it) {
                    UserGroups.USER -> setOf(
                        FrontendPermissions.CREATE,
                        FrontendPermissions.READ,
                        FrontendPermissions.UPDATE,
                        FrontendPermissions.DELETE,
                        FrontendPermissions.SEARCH
                    )
                    UserGroups.TEST -> setOf()
                }
            }.flatten().toSet()

            responseTask.permissions.addAll(permAdd)
        }
    }
}