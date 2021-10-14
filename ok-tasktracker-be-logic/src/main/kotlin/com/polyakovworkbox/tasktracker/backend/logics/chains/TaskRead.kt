package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskReadStub
import com.polyakovworkbox.tasktracker.common.cor.ICorExec
import com.polyakovworkbox.tasktracker.common.cor.chain
import com.polyakovworkbox.tasktracker.validation.cor.validation
import com.polyakovworkbox.tasktracker.validation.lib.validators.StringNotEmptyValidator

object TaskRead: ICorExec<BeContext> by chain<BeContext> ({
    checkOperation("Check that operation is correct", Operation.READ)
    chainInit("Init of the chain")

    taskReadStub("Handling stub cases")

    validation {
        validate<String> { validator(StringNotEmptyValidator("id")); on { this.requestTaskId.id } }
    }

    prepareResponse("Preparing response")


}).build()