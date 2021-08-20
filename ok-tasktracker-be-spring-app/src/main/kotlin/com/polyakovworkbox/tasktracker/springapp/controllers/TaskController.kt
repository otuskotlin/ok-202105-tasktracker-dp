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
import com.polyakovworkbox.tasktracker.backend.common.mapping.toCreateResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toDeleteResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toReadResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toSearchResponse
import com.polyakovworkbox.tasktracker.backend.common.mapping.toUpdateResponse
import com.polyakovworkbox.tasktracker.springapp.services.TaskService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task")
class TaskController(
    val taskService: TaskService
) {
    @PostMapping("create")
    fun createTask(@RequestBody request: CreateTaskRequest): CreateTaskResponse =
        BeContext().mapRequest(request).let {
            taskService.create(it)
        }.toCreateResponse()

    @PostMapping("read")
    fun readTask(@RequestBody request: ReadTaskRequest): ReadTaskResponse =
        BeContext().mapRequest(request).let {
            taskService.read(it)
        }.toReadResponse()

    @PostMapping("update")
    fun updateTask(@RequestBody request: UpdateTaskRequest): UpdateTaskResponse =
        BeContext().mapRequest(request).let {
            taskService.update(it)
        }.toUpdateResponse()

    @PostMapping("delete")
    fun deleteTask(@RequestBody request: DeleteTaskRequest): DeleteTaskResponse =
        BeContext().mapRequest(request).let {
            taskService.delete(it)
        }.toDeleteResponse()

    @PostMapping("search")
    fun searchTask(@RequestBody request: SearchTasksRequest): SearchTasksResponse =
        BeContext().mapRequest(request).let {
            taskService.search(it)
        }.toSearchResponse()
}