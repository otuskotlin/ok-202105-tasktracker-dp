package com.polyakovworkbox.tasktracker.kmp

expect class SuspendRequest() {
    suspend fun request(): String
}