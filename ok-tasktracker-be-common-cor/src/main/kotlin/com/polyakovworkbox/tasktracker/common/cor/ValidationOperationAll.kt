package com.polyakovworkbox.tasktracker.common.cor

class ValidationOperationAll<C> (
    val validators: List<ValidationOperation<C, *>>
) {
    fun validate(context: C) {
        validators.forEach {
            it.validate(context)
        }
    }

}