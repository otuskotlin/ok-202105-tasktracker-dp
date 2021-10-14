package com.polyakovworkbox.tasktracker.validation.lib.validators

import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import com.polyakovworkbox.tasktracker.validation.ValidationFieldError
import com.polyakovworkbox.tasktracker.validation.ValidationResult
import com.polyakovworkbox.tasktracker.validation.Validator


class AtLeastOneTaskValueProvidedValidator(
    val field: String = "Name, one of the descriptions, due time, progress mark, parent or child/children",
    val message: String = "One of the task values should be provided"
) : Validator<Task> {

    override fun validate(data: Task): ValidationResult {
        return if (
            data.name == Name.NONE
            && data.description == Description.NONE
            && data.attainabilityDescription == Description.NONE
            && data.relevanceDescription == Description.NONE
            && data.measurability.description == Description.NONE
            && data.measurability.progress == Percent.NONE
            && data.dueTime == DueTime.NONE
            && data.parent == TaskIdReference.NONE
            && data.children == emptyList<TaskIdReference>()
        ) {
            ValidationResult(
                listOf(
                    ValidationFieldError(
                        message = message,
                        field = field
                    )
                )
            )
        } else {
            ValidationResult.SUCCESS
        }
    }
}