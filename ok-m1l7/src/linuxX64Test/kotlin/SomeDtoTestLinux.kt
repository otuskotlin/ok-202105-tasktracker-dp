package com.polyakovworkbox.tasktracker.kmp

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SomeDtoTestLinux {

    @Test
    fun someDtoTest() {
        assertEquals("str", SomeDto(some = "str").some)
    }

    @Test
    fun requestTest() {
        assertTrue("Request.request must return \"LinuxX64\"") {
            Request().request().contains("LinuxX64")
        }
    }

    @Test
    fun suspendTest() {
        runBlocking {
            assertEquals("Suspend LinuxX64", SuspendRequest().request())
        }
    }
}