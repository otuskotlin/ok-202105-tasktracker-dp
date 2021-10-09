package com.polyakovworkbox.tasktracker.springapp

import com.polyakovworkbox.tasktracker.springapp.controllers.TaskController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OkTasktrackerBeSpringAppApplicationTests {

    @Autowired
    private lateinit var myController: TaskController

    @Test
    fun contextLoads() {
        assertThat(myController).isNotNull
    }

}
