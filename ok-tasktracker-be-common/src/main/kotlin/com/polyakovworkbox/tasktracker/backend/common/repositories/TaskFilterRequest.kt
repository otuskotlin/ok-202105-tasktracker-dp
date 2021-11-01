package com.polyakovworkbox.tasktracker.backend.common.repositories

import com.polyakovworkbox.tasktracker.backend.common.models.general.EqualityMode
import java.time.Instant

data class TaskFilterRequest(
    val nameFilter: String? = null,
    val descriptionFilter: String? = null,
    val attainabilityDescriptionFilter: String? = null,
    val relevanceDescriptionFilter: String? = null,
    val measurabilityDescriptionFilter: String? = null,
    val progressMarkFilter: Int? = null,
    val progressMarkFilterEquality: EqualityMode = EqualityMode.EQUALS,
    val dueTimeFilter: Instant? = null,
    val dueTimeFilterEquality: EqualityMode = EqualityMode.EQUALS,
    val parentIdFilter: String? = null,
    val childIdFilter: String? = null
): ITaskRepoRequest