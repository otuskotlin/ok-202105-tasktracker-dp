package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.logics.workers.accessValidation
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainPermissions
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareTaskForSaving
import com.polyakovworkbox.tasktracker.backend.logics.workers.repo.repoRead
import com.polyakovworkbox.tasktracker.backend.logics.workers.repo.repoUpdate
import com.polyakovworkbox.tasktracker.backend.logics.workers.selectDB
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskUpdateStub
import com.polyakovworkbox.tasktracker.common.cor.ICorExec
import com.polyakovworkbox.tasktracker.common.cor.chain
import com.polyakovworkbox.tasktracker.common.cor.validation
import com.polyakovworkbox.tasktracker.common.handlers.worker
import com.polyakovworkbox.tasktracker.validators.AtLeastOneTaskValueProvidedValidator
import com.polyakovworkbox.tasktracker.validators.NumberInRangeValidator
import com.polyakovworkbox.tasktracker.validators.StringNotEmptyValidator

object TaskUpdate: ICorExec<BeContext> by chain<BeContext> ({

    checkOperation("Check that operation is correct", Operation.UPDATE)
    chainInit("Init of the chain")

    selectDB("selection between prod DB and stub DB")

    taskUpdateStub("Handling stub cases")

    validation {
        validate<String> { validator(StringNotEmptyValidator("id")); on { this.requestTask.id.id } }
        validate<Task> { validator(AtLeastOneTaskValueProvidedValidator()); on { this.requestTask } }
        validate<Int> {
            validator(NumberInRangeValidator(field = "Measurability", min = 0, max = 100))
            on { this.requestTask.measurability.progress.percent }
        }
    }

    chainPermissions("Computing user permissions")
    worker("Initing requestId") { requestTaskId = requestTask.id }
    repoRead("Reading task from DB")

    accessValidation("Validating permissions")
    prepareTaskForSaving("Preparing entity for saving")
    repoUpdate("Update task in DB")

    prepareResponse("Preparing response")

}).build()