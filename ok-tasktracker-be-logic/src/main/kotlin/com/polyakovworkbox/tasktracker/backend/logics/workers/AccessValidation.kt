package com.polyakovworkbox.tasktracker.backend.logics.workers

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.common.cor.ICorChainDsl
import com.polyakovworkbox.tasktracker.common.handlers.chain
import com.polyakovworkbox.tasktracker.common.handlers.worker

fun ICorChainDsl<BeContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { corStatus == CorStatus.RUNNING }
    worker("Вычисление отношения объявления к принципалу") {
        dbTask.principalRelations = dbTask.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к объявлению") {
        permitted = dbTask.principalRelations.flatMap { relation ->
            chainPermissions.map { permission ->
                AccessTableConditions(
                    operation = operation,
                    permission = permission,
                    relation = relation,
                )
            }
        }
            .any {
                accessTable[it] ?: false
            }
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            errors.add(
                ApiError(message = "User is not allowed to this operation")
            )
        }
    }
}