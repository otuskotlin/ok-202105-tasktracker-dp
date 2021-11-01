package com.polyakovworkbox.tasktracker.springapp.configs

import com.polyakovworkbox.tasktracker.backend.common.context.ContextConfig
import com.polyakovworkbox.tasktracker.backend.logics.TaskCrud
import com.polyakovworkbox.tasktracker.repo.inmemory.TaskRepoInMemory
import com.polyakovworkbox.tasktracker.springapp.services.TaskService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
class ServiceConfig {

    @Bean
    fun taskCrud(contextConfig: ContextConfig) =  TaskCrud(contextConfig)

    @Bean
    fun service(taskCrud: TaskCrud): TaskService = TaskService(taskCrud)

    @Bean
    fun contextConfig(): ContextConfig = ContextConfig(
        repoProd = TaskRepoInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
        repoTest = TaskRepoInMemory(initObjects = listOf()),
    )
}