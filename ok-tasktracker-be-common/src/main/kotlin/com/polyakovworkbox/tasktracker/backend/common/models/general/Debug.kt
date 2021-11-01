package com.polyakovworkbox.tasktracker.backend.common.models.general

data class Debug(
    var mode: Mode = Mode.PROD,
    var stub: Stub = Stub.NONE
) {
    companion object {
        val DEFAULT = Debug()
    }
}

enum class Mode {
    PROD, STUB, TEST
}

enum class Stub {
    NONE, SUCCESS, ERROR_DB
}