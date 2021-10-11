package com.polyakovworkbox.tasktracker.backend.logics

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.logics.chains.TaskCreate
import com.polyakovworkbox.tasktracker.backend.logics.chains.TaskDelete
import com.polyakovworkbox.tasktracker.backend.logics.chains.TaskRead
import com.polyakovworkbox.tasktracker.backend.logics.chains.TaskSearch
import com.polyakovworkbox.tasktracker.backend.logics.chains.TaskUpdate

class TaskCrud {
    suspend fun create(context: BeContext) {
        TaskCreate.exec(context.setSettings())
    }
    suspend fun read(context: BeContext) {
        TaskRead.exec(context.setSettings())
    }
    suspend fun update(context: BeContext) {
        TaskUpdate.exec(context.setSettings())
    }
    suspend fun delete(context: BeContext) {
        TaskDelete.exec(context.setSettings())
    }
    suspend fun search(context: BeContext) {
        TaskSearch.exec(context.setSettings())
    }

    private fun BeContext.setSettings() = apply {
        //TODO: introduce additional settings (like repository etc.) if required here
    }
}