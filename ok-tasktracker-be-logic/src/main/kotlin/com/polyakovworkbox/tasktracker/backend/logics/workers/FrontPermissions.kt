package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.permissions.Permission
import com.polyakovworkbox.tasktracker.backend.common.permissions.UserGroups
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.chain
import com.polyakovworkbox.tasktracker.common.handlers.worker

fun ICorChainDsl<BeContext>.frontPermissions(title: String) = chain {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { corStatus == CorStatus.RUNNING }

    worker {
        this.title = "Разрешения для собственного объявления"
        description = this.title
        on { responseTask.ownerId.id == principal.id }
        handle {
            val permAdd: Set<Permission> = principal.groups.map {
                when (it) {
                    UserGroups.USER -> setOf(
                        Permission.READ,
                        Permission.UPDATE,
                        Permission.DELETE,
                    )
                    UserGroups.TEST -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<Permission> = principal.groups.map {
                when (it) {
                    UserGroups.USER -> setOf<Permission>()
                    UserGroups.TEST -> setOf()
                }
            }.flatten().toSet()
            responseTask.permissions.addAll(permAdd)
            responseTask.permissions.removeAll(permDel)
        }
    }

    worker {
        this.title = "Разрешения для модератора"
        description = this.title
        on { responseTask.ownerId.id != principal.id /* && tag, group, ... */ }
        handle {
            val permAdd: Set<Permission> = principal.groups.map {
                when (it) {
                    UserGroups.USER -> setOf<Permission>()
                    UserGroups.TEST -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<Permission> = principal.groups.map {
                when (it) {
                    UserGroups.USER -> setOf(
                        Permission.UPDATE,
                        Permission.DELETE,
                        Permission.CONTACT,
                    )
                    UserGroups.TEST -> setOf()
                }
            }.flatten().toSet()
            responseTask.permissions.addAll(permAdd)
            responseTask.permissions.removeAll(permDel)
        }
    }
    worker {
        this.title = "Разрешения для администратора"
        description = this.title
    }
}