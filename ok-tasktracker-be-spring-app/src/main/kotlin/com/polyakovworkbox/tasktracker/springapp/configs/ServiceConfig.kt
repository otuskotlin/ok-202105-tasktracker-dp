package com.polyakovworkbox.tasktracker.springapp.configs

import com.polyakovworkbox.tasktracker.backend.logics.TaskCrud
import com.polyakovworkbox.tasktracker.springapp.services.TaskService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ServiceConfig {

    @Bean
    fun taskCrud() =  TaskCrud()

    @Bean
    fun service(): TaskService = TaskService(taskCrud())
}