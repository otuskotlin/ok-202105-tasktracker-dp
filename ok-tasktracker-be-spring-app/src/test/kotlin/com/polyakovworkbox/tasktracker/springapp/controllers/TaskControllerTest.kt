package com.polyakovworkbox.tasktracker.springapp.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val om = ObjectMapper().registerKotlinModule()

    @Test
    fun `create stub success`() {
        val headers = HttpHeaders()
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
        headers.setContentType(MediaType.APPLICATION_JSON)
        headers.set("X-TP-DeviceID", "your value")

        val request = CreateTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
            task = CreatableTask(
                name = "some name",
                ownerId = "00560000-0000-0000-0000-000000000001",
                description = "some description"
            )
        )

        val entity: HttpEntity<String> = HttpEntity<String>(om.writeValueAsString(request), headers)

        val response: CreateTaskResponse? = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .exchange(
            "http://localhost:${port}/task/create",
            HttpMethod.POST,
            entity,
            CreateTaskResponse::class.java
        ).body
/*            .postForObject(
            "http://localhost:${port}/task/create",
            request,
            CreateTaskResponse::class.java
        )*/

        assertEquals(ResponseResult.SUCCESS, response!!.result)
        assertEquals("""read "The Lord of the Rings"""", response.task?.name)
    }

    @Test
    fun `create stub error`() {
        val request = CreateTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.ERROR_DB),
            task = CreatableTask(
                name = "some name",
                ownerId = "00560000-0000-0000-0000-000000000001",
                description = "some description"
            )
        )

        val response: CreateTaskResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/create",
            request,
            CreateTaskResponse::class.java
        )

        assertEquals(ResponseResult.ERROR, response.result)
        assertEquals(null, response.task)
        assertTrue(response.errors?.all { it.message == "DB error occurred" } ?: false)
    }


    @Test
    fun `read stub success`() {
        val request = ReadTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
            id = "1"
        )

        val response: ReadTaskResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/read",
            request,
            ReadTaskResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals("""read "The Lord of the Rings"""", response.task?.name)
    }

    @Test
    fun `read stub error`() {
        val request = ReadTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.ERROR_DB),
            id = "1"
        )

        val response: ReadTaskResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/read",
            request,
            ReadTaskResponse::class.java
        )

        assertEquals(ResponseResult.ERROR, response.result)
        assertEquals(null, response.task)
        assertTrue(response.errors?.all { it.message == "DB error occurred" } ?: false)
    }


    @Test
    fun `update stub success`() {
        val request = UpdateTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
            task = UpdatableTask(
                id = "1",
                ownerId = "00560000-0000-0000-0000-000000000001",
                name = "some name",
                description = "some description",
                dueTime = "2021-08-23T18:25:43.511Z"
            )
        )

        val response: UpdateTaskResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/update",
            request,
            UpdateTaskResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals("""2021-08-23T18:25:43.511Z""", response.task?.dueTime)
    }

    @Test
    fun `update stub error`() {
        val request = UpdateTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.ERROR_DB),
            task = UpdatableTask(
                id = "1",
                ownerId = "00560000-0000-0000-0000-000000000001",
                name = "some name",
                description = "some description",
                dueTime = "2021-08-23T18:25:43.511Z"
            )
        )

        val response: UpdateTaskResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/update",
            request,
            UpdateTaskResponse::class.java
        )

        assertEquals(ResponseResult.ERROR, response.result)
        assertEquals(null, response.task)
        assertTrue(response.errors?.all { it.message == "DB error occurred" } ?: false)
    }

    @Test
    fun `delete stub success`() {
        val request = DeleteTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
            id = "1"
        )

        val response: DeleteTaskResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/delete",
            request,
            DeleteTaskResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals("1", response.task?.id)
    }

    @Test
    fun `delete stub error`() {
        val request = DeleteTaskRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.ERROR_DB),
            id = "1"
        )

        val response: DeleteTaskResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/delete",
            request,
            DeleteTaskResponse::class.java
        )

        assertEquals(ResponseResult.ERROR, response.result)
        assertEquals(null, response.task)
        assertTrue(response.errors?.all { it.message == "DB error occurred" } ?: false)
    }

    @Test
    fun `search stub success`() {
        val request = SearchTasksRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
            searchFilter = SearchFilter(
                nameFilter = "The Lord of the Rings"
            )
        )

        val response: SearchTasksResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/search",
            request,
            SearchTasksResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertThat(response.availableTasks?.all { it.name.contains("The Lord of the Rings") })

    }

    @Test
    fun `search stub error`() {
        val request = SearchTasksRequest(
            requestId = "1234567890",
            debug = Debug(Debug.Mode.STUB, Debug.Stub.ERROR_DB),
            searchFilter = SearchFilter(
                nameFilter = "The Lord of the Rings"
            )
        )

        val response: SearchTasksResponse = restTemplate
            .withBasicAuth("taskUser", "taskUserPass")
            .postForObject(
            "http://localhost:${port}/task/search",
            request,
            SearchTasksResponse::class.java
        )

        assertEquals(ResponseResult.ERROR, response.result)
        assertThat(response.availableTasks.isNullOrEmpty())
        assertTrue(response.errors?.all { it.message == "DB error occurred" } ?: false)
    }
}