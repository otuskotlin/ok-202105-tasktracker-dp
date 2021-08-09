package com.polyakovworkbox.tasktracker.backend.common.models.task

data class Task(
    var id: TaskId = TaskId.NONE,
    var name: Name = Name.NONE,
    var description: Description = Description.NONE,
    var attainabilityDescription: Description = Description.NONE,
    var relevanceDescription: Description = Description.NONE,
    var measurability: Measurability = Measurability.NONE,
    var dueTime: DueTime = DueTime.NONE,
    var parent: TaskIdReference = TaskIdReference.NONE,
    var children: List<TaskIdReference> = emptyList()
) {
    companion object {
        val NONE = Task()
    }
}
