package com.polyakovworkbox.tasktracker.repo.postgre

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskFilterRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskIdRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskModelRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskRepoResponse
import com.polyakovworkbox.tasktracker.backend.common.repositories.TasksRepoResponse
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException
import java.time.LocalDateTime
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
        runBlocking {
            initObjects.forEach {
                save(it)
            }
        }
    }

    private suspend fun save(req: Task): TaskRepoResponse {
        return safeTransaction({
            val res = TasksTable.insert {
                if (req.id != TaskId.NONE) {
                    it[id] = req.id.asUUID()
                }
                it[name] = req.name.name
                it[description] = req.description.description
                it[attainabilityDescription] = req.attainabilityDescription.description
                it[relevanceDescription] = req.relevanceDescription.description
                it[measurabilityDescription] = req.measurability.description.description
                it[progress] = req.measurability.progress.percent
                it[dueTime] = LocalDateTime.from(req.dueTime.dueTime)
                it[parent] = req.parent.asUUID()
                it[children] = req.children.singleOrNull()?.asUUID() ?: UUID.fromString("")
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
                it[name] = task.name.name
                it[description] = task.description.description
                it[attainabilityDescription] = task.attainabilityDescription.description
                it[relevanceDescription] = task.relevanceDescription.description
                it[measurabilityDescription] = task.measurability.description.description
                it[progress] = task.measurability.progress.percent
                it[dueTime] = LocalDateTime.from(task.dueTime.dueTime)
                it[parent] = task.parent.asUUID()
                it[children] = task.children.singleOrNull()?.asUUID() ?: UUID.fromString("")
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

    //TODO this is complete bullshit - make normal search here
    override suspend fun search(req: TaskFilterRequest): TasksRepoResponse {
        return safeTransaction({
            // Select only if options are provided
            val results = (TasksTable).select {
                (if (req.searchString.isBlank()) Op.TRUE else TasksTable.id eq UUID.fromString(req.searchString))
/*                (if (req.ownerId == OwnerIdModel.NONE) Op.TRUE else TasksTable.ownerId eq req.asUUID()) and
                        (if (req.dealSide == DealSideModel.NONE) Op.TRUE else AdsTable.dealSide eq req.dealSide)*/
            }

            TasksRepoResponse(result = results.map { TasksTable.from(it) }, isSuccess = true)
        }, {
            TasksRepoResponse(result = emptyList(), isSuccess = false, listOf(ApiError(message = localizedMessage)))
        })
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