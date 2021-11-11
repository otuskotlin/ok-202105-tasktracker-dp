package com.polyakovworkbox.tasktracker.backend.common.models.general

import com.polyakovworkbox.tasktracker.backend.common.permissions.UserGroups

data class Principal(
    var id: String = "",
    var groups: List<UserGroups> = listOf(UserGroups.TEST)
){

    companion object {
        val NONE = Principal()
    }

}