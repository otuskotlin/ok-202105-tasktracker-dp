package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.permissions.UserGroups
import com.polyakovworkbox.tasktracker.backend.common.permissions.BackendPermissions
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.cor.worker


fun ICorChainDsl<BeContext>.backendPermissions(title: String) = worker<BeContext> {
    this.title = title
    description = "Calculating effective permissions of user"

    on {
        corStatus == CorStatus.RUNNING
    }

    handle {
        val permAdd: Set<BackendPermissions> = principal.groups.map {
            when(it) {
                UserGroups.USER -> setOf(
                    BackendPermissions.READ_OWN,
                    BackendPermissions.CREATE_OWN,
                    BackendPermissions.UPDATE_OWN,
                    BackendPermissions.DELETE_OWN,
                    BackendPermissions.SEARCH_OWN
                )
                UserGroups.TEST -> setOf()
            }
        }.flatten().toSet()

        chainPermissions.addAll(permAdd)

    }
}