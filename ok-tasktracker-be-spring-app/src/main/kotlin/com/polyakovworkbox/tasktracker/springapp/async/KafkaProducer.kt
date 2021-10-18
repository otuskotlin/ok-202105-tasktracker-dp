package com.polyakovworkbox.tasktracker.springapp.async

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    private val om = ObjectMapper().registerKotlinModule()

    fun sendMessage(msg: BaseResponse) {
        runBlocking {
            val json = withContext(Dispatchers.IO) { om.writeValueAsString(msg) }

            kafkaTemplate.send("TaskTopic", json)
        }
    }
}