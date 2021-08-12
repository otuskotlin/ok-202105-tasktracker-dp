package com.polyakovworkbox.tasktracker.backend.common.models.task

import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent

data class Measurability(
    var description: Description = Description.NONE,
    var progress: Percent = Percent.NONE
)
