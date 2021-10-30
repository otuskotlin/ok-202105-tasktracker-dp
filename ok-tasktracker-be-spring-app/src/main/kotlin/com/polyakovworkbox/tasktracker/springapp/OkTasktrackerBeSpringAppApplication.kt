package com.polyakovworkbox.tasktracker.springapp

import com.polyakovworkbox.tasktracker.springapp.configs.ConfigProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties::class)
class OkTasktrackerBeSpringAppApplication

fun main(args: Array<String>) {
    runApplication<OkTasktrackerBeSpringAppApplication>(*args)
}
