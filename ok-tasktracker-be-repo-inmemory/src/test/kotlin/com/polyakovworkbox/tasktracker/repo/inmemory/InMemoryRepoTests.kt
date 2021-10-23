package com.polyakovworkbox.tasktracker.repo.inmemory

import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositoryCreateTest
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositoryDeleteTest
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositoryReadTest
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositorySearchTest
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositoryUpdateTest

class TaskInMemoryRepositoryCreateTest : TaskRepositoryCreateTest() {
    override val repo: ITaskRepo = TaskRepoInMemory(
        initObjects = initObjects
    )
}

class TaskInMemoryRepositoryDeleteTest : TaskRepositoryDeleteTest(){
    override val repo: ITaskRepo = TaskRepoInMemory(
        initObjects = initObjects
    )
}

class TaskInMemoryRepositoryReadTest : TaskRepositoryReadTest(){
    override val repo: ITaskRepo = TaskRepoInMemory(
        initObjects = initObjects
    )
}

class TaskInMemoryRepositorySearchTest : TaskRepositorySearchTest(){
    override val repo: ITaskRepo = TaskRepoInMemory(
        initObjects = initObjects
    )
}

class TaskInMemoryRepositoryUpdateTest : TaskRepositoryUpdateTest(){
    override val repo: ITaskRepo = TaskRepoInMemory(
        initObjects = initObjects
    )
}
