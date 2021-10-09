package com.polyakovworkbox.tasktracker.springapp.controllers

import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.CreateTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.Debug
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.DeleteTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ReadTaskResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ResponseResult
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchFilter
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.SearchTasksResponse
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdatableTask
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskRequest
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.UpdateTaskResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `create success`() {
        val request = CreateTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.TEST, Debug.Stub.SUCCESS),
            task = CreatableTask(
                name = "some name",
                description = "some description"
            )
        )

        val response: CreateTaskResponse = restTemplate.postForObject(
            "http://localhost:${port}/task/create",
            request,
            CreateTaskResponse::class.java
        )

        assertEquals(response.result, ResponseResult.SUCCESS)
        assertEquals("""read "The Lord of the Rings"""", response.task?.name)

    }


    @Test
    fun `read success`() {
        val request = ReadTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.TEST, Debug.Stub.SUCCESS),
            id = "1"
        )

        val response: ReadTaskResponse = restTemplate.postForObject(
            "http://localhost:${port}/task/read",
            request,
            ReadTaskResponse::class.java
        )

        assertEquals(response.result, ResponseResult.SUCCESS)
        assertEquals("""read "The Lord of the Rings"""", response.task?.name)

    }


    @Test
    fun `update success`() {
        val request = UpdateTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.TEST, Debug.Stub.SUCCESS),
            task = UpdatableTask(
                id = "1",
                name = "some name",
                description = "some description",
                dueTime = "2021-08-23T18:25:43.511Z"
            )
        )

        val response: UpdateTaskResponse = restTemplate.postForObject(
            "http://localhost:${port}/task/update",
            request,
            UpdateTaskResponse::class.java
        )

        assertEquals(response.result, ResponseResult.SUCCESS)
        assertEquals("""2021-08-23T18:25:43.511Z""", response.task?.dueTime)


    }

    @Test
    fun `delete success`() {
        val request = DeleteTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.TEST, Debug.Stub.SUCCESS),
            id = "1"
        )

        val response: DeleteTaskResponse = restTemplate.postForObject(
            "http://localhost:${port}/task/delete",
            request,
            DeleteTaskResponse::class.java
        )

        assertEquals(response.result, ResponseResult.SUCCESS)
        assertEquals("1", response.task?.id)
    }

    @Test
    fun `search success`() {
        val request = SearchTasksRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.TEST, Debug.Stub.SUCCESS),
            searchFilter = SearchFilter(
                nameFilter = "The Lord of the Rings"
            )
        )

        val response: SearchTasksResponse = restTemplate.postForObject(
            "http://localhost:${port}/task/search",
            request,
            SearchTasksResponse::class.java
        )

        assertEquals(response.result, ResponseResult.SUCCESS)
        assertThat(response.availableTasks?.all { it.name.contains("The Lord of the Rings") })

    }
}