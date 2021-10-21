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

    private suspend fun save(item: Task): TaskRepoResponse {
        return safeTransaction({
            val realOwnerId = UsersTable.insertIgnore {
                if (item.ownerId != OwnerIdModel.NONE) {
                    it[id] = item.ownerId.asUUID()
                }
                it[name] = item.ownerId.asUUID().toString()
            } get UsersTable.id

            val res = TasksTable.insert {
                if (item.id != TaskId.NONE) {
                    it[id] = item.id.asUUID()
                }
                it[name] = item.name
                it[description] = item.description
                it[ownerId] = realOwnerId
                it[visibility] = item.visibility
                it[dealSide] = item.dealSide
            }

            TaskRepoResponse(TasksTable.from(res), true)
        }, {
            TaskRepoResponse(
                result = null,
                isSuccess = false,
                errors = listOf(ApiError(
                    message = localizedMessage))
            )
        })
    }

    override suspend fun create(req: TaskModelRequest): TaskRepoResponse {
        return save(req.ad)
    }

    override suspend fun read(req: TaskIdRequest): TaskRepoResponse {
        return safeTransaction({
            val result = (AdsTable innerJoin UsersTable).select { AdsTable.id.eq(req.id.asUUID()) }.single()

            TaskRepoResponse(AdsTable.from(result), true)
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
        val ad = req.task
        return safeTransaction({
            UsersTable.insertIgnore {
                if (ad.ownerId != OwnerIdModel.NONE) {
                    it[id] = ad.ownerId.asUUID()
                }
                it[name] = ad.ownerId.asUUID().toString()
            }
            UsersTable.update({ UsersTable.id.eq(ad.ownerId.asUUID()) }) {
                it[name] = ad.ownerId.asUUID().toString()
            }

            AdsTable.update({ AdsTable.id.eq(ad.id.asUUID()) }) {
                it[title] = ad.title
                it[description] = ad.description
                it[ownerId] = ad.ownerId.asUUID()
                it[visibility] = ad.visibility
                it[dealSide] = ad.dealSide
            }
            val result = AdsTable.select { AdsTable.id.eq(ad.id.asUUID()) }.single()

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
            val result = AdsTable.select { AdsTable.id.eq(req.id.asUUID()) }.single()
            AdsTable.deleteWhere { AdsTable.id eq req.id.asUUID() }

            TaskRepoResponse(result = AdsTable.from(result), isSuccess = true)
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
            val results = (AdsTable innerJoin UsersTable).select {
                (if (req.ownerId == OwnerIdModel.NONE) Op.TRUE else TasksTable.ownerId eq req.ownerId.asUUID()) and
                        (if (req.dealSide == DealSideModel.NONE) Op.TRUE else AdsTable.dealSide eq req.dealSide)
            }

            TasksRepoResponse(result = results.map { AdsTable.from(it) }, isSuccess = true)
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