package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.logics.workers.accessValidation
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.backendPermissions
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.frontendPermissions
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.repo.repoRead
import com.polyakovworkbox.tasktracker.backend.logics.workers.selectDB
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskReadStub
import com.polyakovworkbox.tasktracker.common.cor.ICorExec
import com.polyakovworkbox.tasktracker.common.cor.chain
import com.polyakovworkbox.tasktracker.common.cor.validation
import com.polyakovworkbox.tasktracker.common.handlers.worker
import com.polyakovworkbox.tasktracker.validators.StringNotEmptyValidator

object TaskRead: ICorExec<BeContext> by chain<BeContext> ({

    checkOperation("Check that operation is correct", Operation.READ)
    chainInit("Init of the chain")

    selectDB("selection between prod DB and stub DB")

    taskReadStub("Handling stub cases")

    validation {
        validate<String> { validator(StringNotEmptyValidator("id")); on { this.requestTaskId.id } }
    }

    backendPermissions("Computing user permissions")
    repoRead("Reading task from DB")
    accessValidation("Validating permissions")

    worker {
        title = "Preparing result for response"
        description = title
        on { corStatus == CorStatus.RUNNING }
        handle { responseTask = dbTask }
    }

    frontendPermissions("Computing permissions to send")

    prepareResponse("Preparing response")

}).build()