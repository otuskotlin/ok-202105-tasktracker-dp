package com.polyakovworkbox.tasktracker.stubs

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter

object TaskStub {

    private val tolkienTask = Task(
        id = TaskId("1"),
        name = Name("read \"The Lord of the Rings\""),
        description = Description("three main books + some additional materials"),
        measurability = Measurability(
            description = Description("can be counted by pages"),
            progress = Percent(0)
        )
    )

    fun getModel() = tolkienTask

    fun getModelUpdated(context: BeContext) = tolkienTask.apply { this.dueTime = context.requestTask.dueTime }

    fun getDeletedModel(context: BeContext) = context.requestTask.apply { id = context.requestTaskId }
}