package com.polyakovworkbox.tasktracker.springapp.controllers

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskResponse
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.mapping.mapRequest
import com.polyakovworkbox.tasktracker.backend.common.mapping.toDeleteResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toReadResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toSearchResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toUpdateResponse
import com.polyakovworkbox.tasktracker.springapp.services.TaskService
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/task")
class TaskController(
    val taskService: TaskService
) {
    @PostMapping("create")
    fun createTask(@RequestBody request: CreateTaskRequest): CreateTaskResponse {
        val context = BeContext(Instant.now())

        return try {
            runBlocking { taskService.create(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.createError(context, e) }
        }
    }

    @PostMapping("read")
    fun readTask(@RequestBody request: ReadTaskRequest): ReadTaskResponse {
        val context = BeContext(Instant.now())

        return try {
            runBlocking { taskService.read(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.readError(context, e) }
        }
    }


    @PostMapping("update")
    fun updateTask(@RequestBody request: UpdateTaskRequest): UpdateTaskResponse {
        val context = BeContext(Instant.now())

        return try {
            runBlocking { taskService.update(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.updateError(context, e) }
        }
    }

    @PostMapping("delete")
    fun deleteTask(@RequestBody request: DeleteTaskRequest): DeleteTaskResponse {
        val context = BeContext(Instant.now())

        return try {
            runBlocking { taskService.delete(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.deleteError(context, e) }
        }
    }

    @PostMapping("search")
    fun searchTask(@RequestBody request: SearchTasksRequest): SearchTasksResponse {
        val context = BeContext(Instant.now())

        return try {
            runBlocking { taskService.search(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.searchError(context, e) }
        }
    }
}