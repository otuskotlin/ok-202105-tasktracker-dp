package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.permissions.UserGroups
import com.polyakovworkbox.tasktracker.backend.common.permissions.UserPermissions
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.cor.worker


fun ICorChainDsl<BeContext>.backendPermissions(title: String) = worker<BeContext> {
    this.title = title
    description = "Calculating effective permissions of user"

    on {
        corStatus == CorStatus.RUNNING
    }

    handle {
        val permAdd: Set<UserPermissions> = principal.groups.map {
            when(it) {
                UserGroups.USER -> setOf(
                    UserPermissions.READ_OWN,
                    UserPermissions.READ_PUBLIC,
                    UserPermissions.CREATE_OWN,
                    UserPermissions.UPDATE_OWN,
                    UserPermissions.DELETE_OWN,
                )
                UserGroups.TEST -> setOf()
                UserGroups.NONE -> setOf()
            }
        }.flatten().toSet()

        chainPermissions.addAll(permAdd)

    }
}