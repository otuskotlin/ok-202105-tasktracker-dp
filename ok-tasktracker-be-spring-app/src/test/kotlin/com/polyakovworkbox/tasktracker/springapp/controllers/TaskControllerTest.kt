package com.polyakovworkbox.tasktracker.springapp.controllers

import com.polyakovworkbox.tasktracker.springapp.services.TaskService
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(controllers = [TaskController::class])
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @SpyBean
    private lateinit var service: TaskService

    @Test
    fun `create success`() {
        mockMvc.perform(post("/task/create")
            .contentType("application/json")
            .content("" +
                "{\n" +
                "  \"messageType\": \"CreateTaskRequest\",\n" +
                "  \"requestId\": \"1234567890\",\n" +
                "  \"task\": {\n" +
                "    \"name\": \"some name\",\n" +
                "    \"description\": \"some description\"\n" +
                "  }\n" +
                "}")).andDo(print()).andExpect(status().isOk)
            .andExpect(content().string(containsString("The Lord of the Rings")))
    }

    @Test
    fun `read success`() {
        mockMvc.perform(post("/task/read")
            .contentType("application/json")
            .content("" +
                    "{\n" +
                    "  \"messageType\": \"ReadTaskRequest\",\n" +
                    "  \"requestId\": \"1234567890\",\n" +
                    "  \"id\": \"1\"\n" +
                    "}")).andDo(print()).andExpect(status().isOk)
            .andExpect(content().string(containsString("The Lord of the Rings")))
    }

    @Test
    fun `update success`() {
        mockMvc.perform(post("/task/update")
            .contentType("application/json")
            .content("" +
                    "{\n" +
                    "  \"messageType\": \"UpdateTaskRequest\",\n" +
                    "  \"requestId\": \"1234567890\",\n" +
                    "  \"task\": {\n" +
                    "    \"id\": \"1\",\n" +
                    "    \"name\": \"some name\",\n" +
                    "    \"description\": \"some description\",\n" +
                    "    \"dueTime\": \"2021-08-23T18:25:43.511Z\"\n" +
                    "  }\n" +
                    "}")).andDo(print()).andExpect(status().isOk)
            .andExpect(content().string(containsString("2021-08-23T18:25:43.511Z")))
    }

    @Test
    fun `delete success`() {
        mockMvc.perform(post("/task/delete")
            .contentType("application/json")
            .content("" +
                    "{\n" +
                    "  \"messageType\": \"DeleteTaskRequest\",\n" +
                    "  \"requestId\": \"1234567890\",\n" +
                    "  \"id\": \"1\"\n" +
                    "}")).andDo(print()).andExpect(status().isOk)
            .andExpect(content().string(containsString("1")))
    }

    @Test
    fun `search success`() {
        mockMvc.perform(post("/task/search")
            .contentType("application/json")
            .content("" +
                    "{\n" +
                    "  \"messageType\": \"SearchTasksRequest\",\n" +
                    "  \"requestId\": \"1234567890\",\n" +
                    "  \"searchFilter\": {\n" +
                    "    \"nameFilter\": \"The Lord of the Rings\"\n" +
                    "  }\n" +
                    "}")).andDo(print()).andExpect(status().isOk)
            .andExpect(content().string(containsString("The Lord of the Rings")))
    }


}