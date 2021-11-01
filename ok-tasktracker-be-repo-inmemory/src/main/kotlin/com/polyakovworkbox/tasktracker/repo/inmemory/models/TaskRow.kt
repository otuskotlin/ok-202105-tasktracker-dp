package com.polyakovworkbox.tasktracker.repo.inmemory.models

import com.polyakovworkbox.tasktracker.backend.common.models.general.Percent
import com.polyakovworkbox.tasktracker.backend.common.models.task.Description
import com.polyakovworkbox.tasktracker.backend.common.models.task.DueTime
import com.polyakovworkbox.tasktracker.backend.common.models.task.Measurability
import com.polyakovworkbox.tasktracker.backend.common.models.task.Name
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskId
import com.polyakovworkbox.tasktracker.backend.common.models.task.TaskIdReference
import java.io.Serializable
import java.time.Instant

data class TaskRow(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    var attainabilityDescription: String? = null,
    var relevanceDescription: String? = null,
    var measurabilityDescription: String? = null,
    var progress: Int? = null,
    var dueTime: Instant = Instant.now(),
    var parent: String? = null,
    var children: List<String> = emptyList()
): Serializable {
    constructor(internal: Task): this(
        id = internal.id.id.takeIf { it.isNotBlank() },
        name = internal.name.name.takeIf { it.isNotBlank() },
        description = internal.description.description.takeIf { it.isNotBlank() },
        attainabilityDescription = internal.attainabilityDescription.description.takeIf { it.isNotBlank() },
        relevanceDescription = internal.relevanceDescription.description.takeIf { it.isNotBlank() },
        measurabilityDescription = internal.measurability.description.description.takeIf { it.isNotBlank() },
        progress = internal.measurability.progress.percent.takeIf { it != Int.MIN_VALUE },
        dueTime = internal.dueTime.dueTime,
        parent = internal.parent.id.takeIf { it.isNotBlank() },
        children = internal.children.map { it.id }
    )

    fun toInternal(): Task = Task(
        id = id?.let { TaskId(it) } ?: TaskId.NONE,
        name = name?.let { Name(it) } ?: Name.NONE,
        description = description?.let { Description(it) } ?: Description.NONE,
        attainabilityDescription = attainabilityDescription?.let { Description(it) } ?: Description.NONE,
        relevanceDescription = relevanceDescription?.let { Description(it) } ?: Description.NONE,
        measurability = Measurability(
            measurabilityDescription?.let { Description(it) } ?: Description.NONE,
            progress?.let { Percent(it) } ?: Percent.NONE
        ),
        dueTime = DueTime(dueTime),
        parent = parent?.let { TaskIdReference(it) } ?: TaskIdReference.NONE,
        children = children.map { TaskIdReference(it) }
    )
}