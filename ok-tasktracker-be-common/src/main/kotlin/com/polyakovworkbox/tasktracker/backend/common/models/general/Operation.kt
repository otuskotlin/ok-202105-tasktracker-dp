package com.polyakovworkbox.tasktracker.backend.common.models.general

import com.polyakovworkbox.tasktracker.backend.common.permissions.UserPermissions

enum class Operation (val permission: UserPermissions? = null) {
    NONE,
    CREATE(UserPermissions.CREATE_OWN),
    READ(UserPermissions.READ_OWN),
    UPDATE(UserPermissions.UPDATE_OWN),
    DELETE(UserPermissions.DELETE_OWN),
    SEARCH(UserPermissions.SEARCH_OWN)
}