package com.polyakovworkbox.tasktracker.backend.common.repositories

interface ITaskRepo {
    suspend fun create(req: TaskModelRequest): TaskRepoResponse
    suspend fun read(req: TaskIdRequest): TaskRepoResponse
    suspend fun update(req: TaskModelRequest): TaskRepoResponse
    suspend fun delete(req: TaskIdRequest): TaskRepoResponse
    suspend fun search(req: TaskFilterRequest): TasksRepoResponse

    object NONE : ITaskRepo {
        override suspend fun create(req: TaskModelRequest): TaskRepoResponse {
            TODO("Not yet implemented")
        }

        override suspend fun read(req: TaskIdRequest): TaskRepoResponse {
            TODO("Not yet implemented")
        }

        override suspend fun update(req: TaskModelRequest): TaskRepoResponse {
            TODO("Not yet implemented")
        }

        override suspend fun delete(req: TaskIdRequest): TaskRepoResponse {
            TODO("Not yet implemented")
        }

        override suspend fun search(req: TaskFilterRequest): TasksRepoResponse {
            TODO("Not yet implemented")
        }

    }
}