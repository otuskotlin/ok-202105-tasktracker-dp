package com.polyakovworkbox.tasktracker.backend.common.models.task

class Task(
    var id: TaskId = TaskId.NONE,
    var name: Name = Name.NONE,
    var description: Description = Description.NONE,
    var attainabilityDescription: Description = Description.NONE,
    var relevanceDescription: Description = Description.NONE,
    var measurability: Measurability = Measurability.NONE,
    var dueTime: DueTime = DueTime.NONE,
    var parent: TaskId = TaskId.NONE,
    var children: List<TaskId> = emptyList()
) {
    companion object {
        val NONE = Task()
    }
}
