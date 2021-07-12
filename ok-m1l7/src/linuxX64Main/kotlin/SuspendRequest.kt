package com.polyakovworkbox.tasktracker.kmp

import kotlinx.coroutines.delay

actual class SuspendRequest actual constructor() {
    actual suspend fun request(): String {
        delay(1000L)
        return "Suspend LinuxX64"
    }
}