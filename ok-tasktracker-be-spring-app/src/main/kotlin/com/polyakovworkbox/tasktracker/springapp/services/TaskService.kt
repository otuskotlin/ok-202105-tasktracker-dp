package com.polyakovworkbox.tasktracker.springapp.services

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.context.ResponseStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.ApiError
import com.polyakovworkbox.tasktracker.stubs.TaskStub
import org.springframework.stereotype.Service

@Service
class TaskService {

    fun create(context: BeContext): BeContext =
        context.apply {
            responseTask = TaskStub.getModel()
        }

    fun read(context: BeContext): BeContext {
        val requestedId = context.requestTaskId.id
        val isCorrect = TaskStub.isCorrectId(requestedId)

        return if (isCorrect) {
            context.apply {
                responseTask = TaskStub.getModel()
            }
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task with id $requestedId cannot be found"
                    )
                )
            }
        }
    }

    fun update(context: BeContext): BeContext =
        context.apply {
            responseTask = requestTask
        }

    fun delete(context: BeContext): BeContext {
        val requestedId = context.requestTaskId.id
        val isCorrect = TaskStub.isCorrectId(requestedId)

        return if (isCorrect) {
            context.apply {
                responseTask = requestTask
            }
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task with id $requestedId cannot be found"
                    )
                )
            }
        }
    }

    fun search(context: BeContext): BeContext {
        val searchCriteria = context.searchFilter
        val exists = TaskStub.taskWithCriteriaExists(searchCriteria)

        return if (exists) {
            context.apply {
                responseTasks = mutableListOf(TaskStub.getModel())
            }
        } else {
            context.apply {
                status = ResponseStatus.ERROR
                errors.add(
                    ApiError(
                        message = "Task with given criteria cannot be found"
                    )
                )
            }
        }
    }
}
