package com.polyakovworkbox.tasktracker.backend.common.models.task.filter

import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode
import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference

data class SearchFilter(
    var nameFilter: Name = Name.NONE,
    var descriptionFilter: Description = Description.NONE,
    var attainabilityDescriptionFilter: Description = Description.NONE,
    var relevanceDescriptionFilter: Description = Description.NONE,
    var measurabilityDescriptionFilter: Description = Description.NONE,
    var progressMarkFilter: Percent = Percent.NONE,
    var progressMarkFilterEquality: EqualityMode = EqualityMode.NONE,
    var dueTimeFilter: DueTime = DueTime.NONE,
    var dueTimeFilterEquality: EqualityMode = EqualityMode.NONE,
    var parentIdFilter: TaskIdReference = TaskIdReference.NONE,
    var childIdFilter: TaskIdReference = TaskIdReference.NONE
) {
    companion object {
        val ALL = SearchFilter()
    }
}