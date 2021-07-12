package com.polyakovworkbox.tasktracker.kmp

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.promise
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

val scope = MainScope()

class SomeDtoTestJs {

    @Test
    fun someDtoTest() {
        assertEquals("str", SomeDto(some = "str").some)
    }

    @Test
    fun requestTest() {
        assertTrue("Request.request must return \"JS\"") {
            Request().request().contains("JS")
        }
    }

    @Test
    fun suspendTest() : dynamic {
        return scope.promise {
            assertEquals("Suspend JS", SuspendRequest().request())
        }
    }
}