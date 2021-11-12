package com.polyakovworkbox.tasktracker.repo.postgre

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskFilterRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskIdRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskModelRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskRepoResponse
import com.polyakovworkbox.tasktracker.backend.common.repositories.TasksRepoResponse
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.NoSuchElementException


class TaskRepoSql(
    url: String = "jdbc:postgresql://localhost:5432/tasktrackerdevdb",
    user: String = "postgres",
    password: String = "tasktracker-pass",
    schema: String = "tasktracker",
    private val initObjects: List<Task> = listOf()
) : ITaskRepo {

    private val db by lazy { SqlConnector(url, user, password, schema).connect(TasksTable) }

    init {
            initObjects.forEach {
                safeTransaction({
                    runBlocking {
                        TasksTable.deleteWhere { TasksTable.id eq it.id.asUUID() }
                        save(it)
                    }
                }, {
                TaskRepoResponse(
                    result = null,
                    isSuccess = false,
                    errors = listOf(
                        ApiError(
                            message = "cannot init")
                    )
                )
            })
        }
    }

    private suspend fun save(req: Task): TaskRepoResponse {
        return safeTransaction({
            val res = TasksTable.insert {
                mapToTableUnit(it, req)
            }

            TaskRepoResponse(TasksTable.from(res), true)
        }, {
            TaskRepoResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    ApiError(
                    message = localizedMessage)
                )
            )
        })
    }

    override suspend fun create(req: TaskModelRequest): TaskRepoResponse {
        return save(req.task)
    }

    override suspend fun read(req: TaskIdRequest): TaskRepoResponse {
        return safeTransaction({
            val result = TasksTable.select { TasksTable.id.eq(req.asUUID()) }.single()

            TaskRepoResponse(TasksTable.from(result), true)
        }, {
            val err = when (this) {
                is NoSuchElementException -> ApiError(field = "id", message = "Not Found")
                is IllegalArgumentException -> ApiError(message = "More than one element with the same id")
                else -> ApiError(message = localizedMessage)
            }
            TaskRepoResponse(result = null, isSuccess = false, errors = listOf(err))
        })
    }

    override suspend fun update(req: TaskModelRequest): TaskRepoResponse {
        val task = req.task
        return safeTransaction({

            TasksTable.update({ TasksTable.id.eq(task.id.asUUID()) }) {
                mapToTableUnit(it, task)
            }
            val result = TasksTable.select { TasksTable.id.eq(task.id.asUUID()) }.single()

            TaskRepoResponse(result = TasksTable.from(result), isSuccess = true)
        }, {
            TaskRepoResponse(
                result = null,
                isSuccess = false,
                errors = listOf(ApiError(field = "id", message = "Not Found"))
            )
        })
    }

    override suspend fun delete(req: TaskIdRequest): TaskRepoResponse {
        return safeTransaction({
            val result = TasksTable.select { TasksTable.id.eq(req.asUUID()) }.single()
            TasksTable.deleteWhere { TasksTable.id eq req.asUUID() }

            TaskRepoResponse(result = TasksTable.from(result), isSuccess = true)
        }, {
            TaskRepoResponse(
                result = null,
                isSuccess = false,
                errors = listOf(ApiError(field = "id", message = "Not Found"))
            )
        })
    }

    override suspend fun search(req: TaskFilterRequest): TasksRepoResponse {
        return safeTransaction({
            // Select only if options are provided
            val results = (TasksTable).select { (
                    (if(req.ownerId?.isBlank() != false) Op.TRUE else TasksTable.ownerId eq (req.ownerId ?: ""))
                ).and(
                    (if(req.nameFilter?.isBlank() != false) Op.TRUE else TasksTable.name eq (req.nameFilter ?: ""))
                ).and(
                    (if(req.descriptionFilter?.isBlank() != false) Op.TRUE else TasksTable.description eq (req.descriptionFilter ?: ""))
                ).and(
                    (if(req.attainabilityDescriptionFilter?.isBlank() != false) Op.TRUE else TasksTable.attainabilityDescription eq (req.attainabilityDescriptionFilter ?: ""))
                ).and(
                    (if(req.relevanceDescriptionFilter?.isBlank() != false) Op.TRUE else TasksTable.relevanceDescription eq (req.relevanceDescriptionFilter ?: ""))
                ).and(
                    (if(req.measurabilityDescriptionFilter?.isBlank() != false) Op.TRUE else TasksTable.measurabilityDescription eq (req.measurabilityDescriptionFilter ?: ""))
                ).and(
                    (if(req.progressMarkFilter == null) Op.TRUE else {
                        when(req.progressMarkFilterEquality) {
                            EqualityMode.NONE,
                            EqualityMode.EQUALS -> TasksTable.progress eq (req.progressMarkFilter ?: 0)
                            EqualityMode.LESS_THAN -> TasksTable.progress less (req.progressMarkFilter ?: 0)
                            EqualityMode.MORE_THAN -> TasksTable.progress greater (req.progressMarkFilter ?: 0)
                        }
                    })
                ).and(
                    (if(req.dueTimeFilter == null) Op.TRUE else {
                        when(req.dueTimeFilterEquality) {
                            EqualityMode.NONE,
                            EqualityMode.EQUALS -> TasksTable.dueTime eq (LocalDateTime.ofInstant(req.dueTimeFilter, ZoneId.of("UTC+03:00")))
                            EqualityMode.LESS_THAN -> TasksTable.dueTime less (LocalDateTime.ofInstant(req.dueTimeFilter, ZoneId.of("UTC+03:00")))
                            EqualityMode.MORE_THAN -> TasksTable.dueTime greater (LocalDateTime.ofInstant(req.dueTimeFilter, ZoneId.of("UTC+03:00")))
                        }
                    })
                ).and(
                    (if(req.parentIdFilter?.isBlank() != false) Op.TRUE else TasksTable.parent eq (UUID.fromString(req.parentIdFilter)))
                ).and(
                    (if(req.childIdFilter?.isBlank() != false) Op.TRUE else TasksTable.children eq (UUID.fromString(req.childIdFilter)))
                )
            }

            TasksRepoResponse(result = results.map { TasksTable.from(it) }, isSuccess = true)
        }, {
            TasksRepoResponse(result = emptyList(), isSuccess = false, listOf(ApiError(message = localizedMessage)))
        })

    }

    private fun TasksTable.mapToTableUnit(
        it: UpdateBuilder<Int>,
        req: Task
    ) {
        if(req.id != TaskId.NONE) {
            it[id] = req.id.asUUID()
        }
        it[name] = req.name.name
        it[description] = req.description.description
        it[attainabilityDescription] = req.attainabilityDescription.description
        it[relevanceDescription] = req.relevanceDescription.description
        it[measurabilityDescription] = req.measurability.description.description
        it[progress] = req.measurability.progress.percent
        it[dueTime] = LocalDateTime.ofInstant(req.dueTime.dueTime, ZoneId.of("UTC+03:00"))
        it[parent] = req.parent.asUUID()
        it[children] = req.children.singleOrNull()?.asUUID() ?: TaskIdReference.NONE.asUUID()
    }

    /**
     * Transaction wrapper to safely handle caught exception and throw all sql-like exceptions. Also remove lot's of duplication code
     */
    private fun <T> safeTransaction(statement: Transaction.() -> T, handleException: Throwable.() -> T): T {
        return try {
            transaction(db, statement)
        } catch (e: SQLException) {
            throw e
        } catch (e: Throwable) {
            return handleException(e)
        }
    }
}