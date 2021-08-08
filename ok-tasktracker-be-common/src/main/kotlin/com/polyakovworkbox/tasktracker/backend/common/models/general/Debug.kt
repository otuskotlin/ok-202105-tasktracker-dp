package com.polyakovworkbox.tasktracker.backend.common.models.general

class Debug(
    var mode: Mode,
    var stub: Stub
) {
    companion object {
        val DEFAULT = Debug(Mode.PROD, Stub.NONE)
    }
}

enum class Mode {
    PROD, TEST
}

enum class Stub {
    NONE, SUCCESS, ERROR_DB
}