package com.polyakovworkbox.tasktracker.backend.common.models.task

class TaskIdReference (
    val id: String
) {
    companion object {
        val NONE = TaskIdReference("")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskIdReference

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}

