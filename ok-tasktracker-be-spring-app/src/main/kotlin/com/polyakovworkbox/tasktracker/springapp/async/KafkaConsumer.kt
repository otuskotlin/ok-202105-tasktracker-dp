package com.polyakovworkbox.tasktracker.springapp.async

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseMessage
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseResponse
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.springapp.services.TaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.time.Instant


@Component
class KafkaConsumer {

    @Autowired
    private lateinit var taskService: TaskService

    @Autowired
    private lateinit var kafkaProducer: KafkaProducer

    private val om = ObjectMapper().registerKotlinModule()

    @KafkaListener(topics = ["TaskTopic"])
    fun receive(consumerRecord: ConsumerRecord<String, String>) {
        runBlocking {
            val beContext = BeContext().apply {
                this.startTime = Instant.now()
            }
            val baseMessage: BaseMessage = om.readValue(consumerRecord.value(), BaseMessage::class.java)
            withContext(Dispatchers.IO) {
                val result: BaseResponse = taskService.handleAsyncTaskRequest(beContext, baseMessage)
                kafkaProducer.sendMessage(result)
            }
        }
    }

}