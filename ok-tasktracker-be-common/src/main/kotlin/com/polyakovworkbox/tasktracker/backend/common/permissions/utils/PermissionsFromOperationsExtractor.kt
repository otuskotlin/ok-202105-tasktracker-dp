package com.polyakovworkbox.tasktracker.backend.common.permissions.utils

import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.permissions.BackendPermissions

fun Operation.getRelatedPermissions() : List<BackendPermissions> =
    when(this) {
        Operation.CREATE -> listOf(BackendPermissions.CREATE_OWN)
        Operation.READ -> listOf(BackendPermissions.READ_OWN)
        Operation.UPDATE -> listOf(BackendPermissions.UPDATE_OWN)
        Operation.DELETE -> listOf(BackendPermissions.DELETE_OWN)
        Operation.SEARCH -> listOf(BackendPermissions.SEARCH_OWN)
        Operation.NONE -> emptyList()
    }