package com.polyakovworkbox.tasktracker.springapp.controllers

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseResponse
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
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.general.Principal
import com.polyakovworkbox.tasktracker.springapp.services.TaskService
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
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
    fun createTask(@RequestBody request: CreateTaskRequest): BaseResponse {
        val context = BeContext(startTime = Instant.now())

        val user: User = SecurityContextHolder.getContext().authentication.principal as User
        context.principal = Principal(user.username)

        context.operation = Operation.CREATE

        return try {
            runBlocking { taskService.create(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.toError(context, e, ::CreateTaskResponse) }
        }
    }

    @PostMapping("read")
    fun readTask(@RequestBody request: ReadTaskRequest): BaseResponse {
        val context = BeContext(startTime = Instant.now())

        val user: User = SecurityContextHolder.getContext().authentication.principal as User
        context.principal = Principal(user.username)

        context.operation = Operation.READ

        return try {
            runBlocking { taskService.read(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.toError(context, e, ::ReadTaskResponse) }
        }
    }


    @PostMapping("update")
    fun updateTask(@RequestBody request: UpdateTaskRequest): BaseResponse {
        val context = BeContext(startTime = Instant.now())

        val user: User = SecurityContextHolder.getContext().authentication.principal as User
        context.principal = Principal(user.username)

        context.operation = Operation.UPDATE

        return try {
            runBlocking { taskService.update(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.toError(context, e, ::UpdateTaskResponse) }
        }
    }

    @PostMapping("delete")
    fun deleteTask(@RequestBody request: DeleteTaskRequest): BaseResponse {
        val context = BeContext(startTime = Instant.now())

        val user: User = SecurityContextHolder.getContext().authentication.principal as User
        context.principal = Principal(user.username)

        context.operation = Operation.DELETE

        return try {
            runBlocking { taskService.delete(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.toError(context, e, ::DeleteTaskResponse) }
        }
    }

    @PostMapping("search")
    fun searchTask(@RequestBody request: SearchTasksRequest): BaseResponse {
        val context = BeContext(startTime = Instant.now())

        val user: User = SecurityContextHolder.getContext().authentication.principal as User
        context.principal = Principal(user.username)

        context.operation = Operation.SEARCH

        return try {
            runBlocking { taskService.search(context, request) }
        } catch (e: Throwable) {
            runBlocking { taskService.toError(context, e, ::SearchTasksResponse) }
        }
    }
}