package com.polyakovworkbox.tasktracker.repo.postgre

import com.polyakovworkbox.tasktracker.backend.common.repositories.ITaskRepo
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositoryCreateTest
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositoryDeleteTest
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositoryReadTest
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositorySearchTest
import com.polyakovworkbox.tasktracker.repo.test.TaskRepositoryUpdateTest

class TaskSQLRepositoryCreateTest : TaskRepositoryCreateTest() {
    override val repo: ITaskRepo = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class TaskSQLRepositoryReadTest : TaskRepositoryReadTest() {
    override val repo: ITaskRepo = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class TaskSQLRepositoryUpdateTest : TaskRepositoryUpdateTest() {
    override val repo: ITaskRepo = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class TaskSQLRepositoryDeleteTest : TaskRepositoryDeleteTest() {
    override val repo: ITaskRepo = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

class TaskSQLRepositorySearchTest : TaskRepositorySearchTest() {
    override val repo: ITaskRepo = SqlTestCompanion.repoUnderTestContainer(initObjects)
}

