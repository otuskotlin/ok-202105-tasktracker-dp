package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.task.Task
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskUpdateStub
import com.polyakovworkbox.tasktracker.common.cor.ICorExec
import com.polyakovworkbox.tasktracker.common.cor.chain
import com.polyakovworkbox.tasktracker.validation.cor.validation
import com.polyakovworkbox.tasktracker.validation.lib.validators.AtLeastOneTaskValueProvidedValidator
import com.polyakovworkbox.tasktracker.validation.lib.validators.NumberInRangeValidator
import com.polyakovworkbox.tasktracker.validation.lib.validators.StringNotEmptyValidator

object TaskUpdate: ICorExec<BeContext> by chain<BeContext> ({

    checkOperation("Check that operation is correct", Operation.UPDATE)
    chainInit("Init of the chain")

    taskUpdateStub("Handling stub cases")

    validation {
        validate<String> { validator(StringNotEmptyValidator("id")); on { this.requestTask.id.id } }
        validate<Task> { validator(AtLeastOneTaskValueProvidedValidator()); on { this.requestTask } }
        validate<Int> {
            validator(NumberInRangeValidator(field = "Measurability", min = 0, max = 100))
            on { this.requestTask.measurability.progress.percent }
        }
    }

    prepareResponse("Preparing response")

}).build()