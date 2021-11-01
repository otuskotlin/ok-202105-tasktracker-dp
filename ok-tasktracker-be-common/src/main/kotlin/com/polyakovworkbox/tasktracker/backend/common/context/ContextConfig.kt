package com.polyakovworkbox.tasktracker.backend.common.context

import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo

data class ContextConfig(
    val repoProd: ITaskRepo = ITaskRepo.NONE,
    val repoTest: ITaskRepo = ITaskRepo.NONE,
)