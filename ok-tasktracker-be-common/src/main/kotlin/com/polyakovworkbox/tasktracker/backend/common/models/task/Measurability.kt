package com.polyakovworkbox.tasktracker.backend.common.models.task

import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent

class Measurability(
    var description: Description = Description.NONE,
    var progress: Percent = Percent.NONE
) {
    companion object {
        val NONE = Measurability()
    }
}
