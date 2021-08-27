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

    fun isCorrectId(id: String): Boolean =
        id == tolkienTask.id.id

    fun taskWithCriteriaExists(filter: SearchFilter): Boolean {
        val nameMatches = filter.nameFilter.name.isNotBlank() &&
                tolkienTask.name.name.contains(filter.nameFilter.name, true)
        val descriptionMatches = filter.descriptionFilter.description.isNotBlank() &&
                tolkienTask.description.description.contains(filter.descriptionFilter.description, true)
        val mDescriptionMatches = filter.measurabilityDescriptionFilter.description.isNotBlank() &&
                tolkienTask.measurability.description.description.contains(filter.measurabilityDescriptionFilter.description, true)
        val progressMatches = filter.progressMarkFilter.percent != Int.MIN_VALUE &&
                tolkienTask.measurability.progress.percent == filter.progressMarkFilter.percent

        return nameMatches
                || descriptionMatches
                || mDescriptionMatches
                || progressMatches
    }



}