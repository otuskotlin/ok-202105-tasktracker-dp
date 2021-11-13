package com.polyakovworkbox.tasktracker.springapp.mappers

import com.polyakovworkbox.tasktracker.backend.common.permissions.UserGroups

fun Set<String>.toUserGroups(): List<UserGroups> =
    this.map {
        when (it) {
            "USER" -> UserGroups.USER
            "TEST" -> UserGroups.TEST
            else -> throw SecurityException("unknown permission group encountered")
        }
    }
