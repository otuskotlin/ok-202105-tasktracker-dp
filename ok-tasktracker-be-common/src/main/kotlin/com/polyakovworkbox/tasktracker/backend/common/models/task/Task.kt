package com.polyakovworkbox.tasktracker.backend.common.models.task

import com.polyakovworkbox.tasktracker.backend.common.permissions.Permission

data class Task(
    var id: TaskId = TaskId.NONE,
    var ownerId: OwnerId = OwnerId.NONE,
    var name: Name = Name.NONE,
    var description: Description = Description.NONE,
    var attainabilityDescription: Description = Description.NONE,
    var relevanceDescription: Description = Description.NONE,
    var measurability: Measurability = Measurability(),
    var dueTime: DueTime = DueTime.NONE,
    var parent: TaskIdReference = TaskIdReference.NONE,
    var children: List<TaskIdReference> = emptyList(),
    var permissions: MutableList<Permission> = mutableListOf()
)
