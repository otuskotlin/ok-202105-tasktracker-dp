package com.polyakovworkbox.tasktracker.backend.common.models.general

import com.polyakovworkbox.tasktracker.backend.common.permissions.BackendPermissions

enum class Operation (val permission: BackendPermissions? = null) {
    NONE,
    CREATE(BackendPermissions.CREATE_OWN),
    READ(BackendPermissions.READ_OWN),
    UPDATE(BackendPermissions.UPDATE_OWN),
    DELETE(BackendPermissions.DELETE_OWN),
    SEARCH(BackendPermissions.SEARCH_OWN)
}