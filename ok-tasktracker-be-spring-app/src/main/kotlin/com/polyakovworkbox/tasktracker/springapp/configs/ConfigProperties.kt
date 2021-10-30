package com.polyakovworkbox.tasktracker.springapp.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "kafka")
class ConfigProperties {
    lateinit var bootstrapAddress: String
    lateinit var topic: String
    lateinit var groupId: String
}