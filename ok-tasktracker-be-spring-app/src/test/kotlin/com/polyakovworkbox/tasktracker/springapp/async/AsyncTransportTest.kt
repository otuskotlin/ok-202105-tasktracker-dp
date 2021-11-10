package com.polyakovworkbox.tasktracker.springapp.async

import com.fasterxml.jackson.databind.ObjectMapper
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.ApiError
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
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class AsyncTransportTest {

    @Autowired
    lateinit var kafkaProducer: KafkaProducer

    @Autowired
    lateinit var kafkaConsumer: KafkaConsumer

    @Autowired
    lateinit var objectMapper: ObjectMapper


    companion object {
        private val TOPIC: String = "TaskTopic"
    }

    @Test
    fun `create task test async`() {
        val request = objectMapper.writeValueAsString(
            CreateTaskRequest(
                requestId = "1234567890",
                debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
                task = CreatableTask(
                    name = "some name",
                    ownerId = "00560000-0000-0000-0000-000000000001",
                    description = "some description"
                )
            )
        )

        kafkaConsumer.receive(ConsumerRecord(
            TOPIC, 0, 0, "key", request
        ))

        val response = objectMapper.readValue(
            kafkaProducer.messageBeingSent,
            CreateTaskResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals(emptyList<ApiError>(), response.errors)
        assertEquals("1", response.task?.id)
    }

    @Test
    fun `read task test async`() {
        val request = objectMapper.writeValueAsString(
            ReadTaskRequest(
                requestId = "1234567890",
                debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
                id = "1"
            )
        )

        kafkaConsumer.receive(ConsumerRecord(
            TOPIC, 0, 0, "key", request
        ))

        val response = objectMapper.readValue(
            kafkaProducer.messageBeingSent,
            ReadTaskResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals(emptyList<ApiError>(), response.errors)
        assertEquals("1", response.task?.id)
    }

    @Test
    fun `update task test async`() {
        val request = objectMapper.writeValueAsString(
            UpdateTaskRequest(
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
        )

        kafkaConsumer.receive(ConsumerRecord(
            TOPIC, 0, 0, "key", request
        ))

        val response = objectMapper.readValue(
            kafkaProducer.messageBeingSent,
            UpdateTaskResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals(emptyList<ApiError>(), response.errors)
        assertEquals("1", response.task?.id)
    }

    @Test
    fun `delete task test async`() {
        val request = objectMapper.writeValueAsString(
            DeleteTaskRequest(
                requestId = "1234567890",
                debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
                id = "1"
            )
        )

        kafkaConsumer.receive(ConsumerRecord(
            TOPIC, 0, 0, "key", request
        ))

        val response = objectMapper.readValue(
            kafkaProducer.messageBeingSent,
            DeleteTaskResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals(emptyList<ApiError>(), response.errors)
        assertEquals("1", response.task?.id)
    }

    @Test
    fun `search task test async`() {
        val request = objectMapper.writeValueAsString(
            SearchTasksRequest(
                requestId = "1234567890",
                debug = Debug(Debug.Mode.STUB, Debug.Stub.SUCCESS),
                searchFilter = SearchFilter(
                    nameFilter = "The Lord of the Rings"
                )
            )
        )

        kafkaConsumer.receive(ConsumerRecord(
            TOPIC, 0, 0, "key", request
        ))

        val response = objectMapper.readValue(
            kafkaProducer.messageBeingSent,
            SearchTasksResponse::class.java
        )

        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals(emptyList<ApiError>(), response.errors)
        assertEquals(1, response.availableTasks?.size)
        assertEquals("1", response.availableTasks?.get(0)?.id)
    }



}