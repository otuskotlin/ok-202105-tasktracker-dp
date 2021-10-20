package com.polyakovworkbox.tasktracker.repo.inmemory

import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskFilterRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskIdRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskModelRequest
import com.polyakovworkbox.tasktracker.backend.common.repositories.TaskRepoResponse
import com.polyakovworkbox.tasktracker.backend.common.repositories.TasksRepoResponse
import com.polyakovworkbox.tasktracker.repo.inmemory.models.TaskRow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import java.time.Duration
import java.util.*

class TaskRepoInMemory(
    private val initObjects: List<Task> = listOf(),
    private val ttl: Duration = Duration.ofMinutes(1)
) : ITaskRepo {

    private val cache: Cache<String, TaskRow> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "tasktracker-task-cache",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    TaskRow::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(ttl))
                .build()
        )
    }

    init {
        runBlocking { initObjects.forEach {
            save(it)
        } }
    }

    private suspend fun save(item: Task): TaskRepoResponse {
        val row = TaskRow(item)
        if (row.id == null) {
            return TaskRepoResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    ApiError(
                        field = "id",
                        message = "Id must not be null or blank"
                    )
                )
            )
        }
        cache.put(row.id, row)
        return TaskRepoResponse(
            result = row.toInternal(),
            isSuccess = true
        )
    }

    override suspend fun create(req: TaskModelRequest): TaskRepoResponse =
        save(req.task.copy(id = TaskId(UUID.randomUUID().toString())))

    override suspend fun read(req: TaskIdRequest): TaskRepoResponse = cache.get(req.id)?.let {
        TaskRepoResponse(
            result = it.toInternal(),
            isSuccess = true
        )
    } ?: TaskRepoResponse(
        result = null,
        isSuccess = false,
        errors = listOf(
            ApiError(
                field = "id",
                message = "Not Found"
            )
        )
    )

    override suspend fun update(req: TaskModelRequest): TaskRepoResponse {
        val key = req.task.id.takeIf { it != TaskId.NONE }?.id
            ?: return TaskRepoResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    ApiError(
                        field = "id",
                        message = "Id must not be null or blank"
                    )
                )
            )

        return if (cache.containsKey(key)) {
            save(req.task)
            TaskRepoResponse(
                result = req.task,
                isSuccess = true
            )
        } else {
            TaskRepoResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    ApiError(
                        field = "id",
                        message = "Not Found"
                    )
                )
            )
        }
    }

    override suspend fun delete(req: TaskIdRequest): TaskRepoResponse {
        val key = req.id.takeIf { it.isNotBlank() }
            ?: return TaskRepoResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    ApiError(
                        field = "id",
                        message = "Id must not be null or blank"
                    )
                )
            )
        val row = cache.get(key) ?: return TaskRepoResponse(
            result = null,
            isSuccess = false,
            errors = listOf(
                ApiError(
                    field = "id",
                    message = "Not Found"
                )
            )
        )
        cache.remove(key)
        return TaskRepoResponse(
            result = row.toInternal(),
            isSuccess = true,
        )
    }

    //TODO: Search through search string?
    override suspend fun search(req: TaskFilterRequest): TasksRepoResponse {
/*        val results = cache.asFlow()
            .filter {
                if (req.ownerId == OwnerIdModel.NONE) return@filter true
                req.ownerId.asString() == it.value.ownerId
            }
            .filter {
                if (req.dealSide == DealSideModel.NONE) return@filter true
                req.dealSide.name == it.value.dealSide
            }
            .map { it.value.toInternal() }
            .toList()
        return TasksRepoResponse(
            result = results,
            isSuccess = true
        )*/
        return TasksRepoResponse(
            result = emptyList(),
            isSuccess = true
        )
    }
}