package com.polyakovworkbox.tasktracker.repo.test

import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.OwnerId
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import java.util.*

abstract class BaseInitTasks : IInitObjects<Task> {

    fun createInitTestModel(
        name: String,
    ) = Task(
        id = TaskId.getRandom(),
        ownerId = OwnerId("00560000-0000-0000-0000-000000000001"),
        name = Name("$name stub"),
        description = Description("$name stub description")
    )

    fun createHalfDoneTestModel(
        name: String,
    ) = Task(
        id = TaskId.getRandom(),
        ownerId = OwnerId("00560000-0000-0000-0000-000000000001"),
        name = Name("$name stub"),
        description = Description("$name stub description"),
        measurability = Measurability(
            progress = Percent(50)
        )


    )
}