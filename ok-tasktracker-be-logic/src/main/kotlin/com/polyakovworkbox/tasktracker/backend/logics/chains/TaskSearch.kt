package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskSearchStub
import com.polyakovworkbox.tasktracker.common.cor.ICorExec
import com.polyakovworkbox.tasktracker.common.cor.chain
import com.polyakovworkbox.tasktracker.validation.cor.validation
import com.polyakovworkbox.tasktracker.validation.lib.validators.DueTimeWithoutEqualityModeValidator
import com.polyakovworkbox.tasktracker.validation.lib.validators.PercentWithoutEqualityModeValidator

object TaskSearch: ICorExec<BeContext> by chain<BeContext> ({

    checkOperation("Check that operation is correct", Operation.SEARCH)
    chainInit("Init of the chain")

    taskSearchStub("Handling stub cases")

    validation {
        validate<SearchFilter> { validator(PercentWithoutEqualityModeValidator()); on { this.searchFilter } }
        validate<SearchFilter> { validator(DueTimeWithoutEqualityModeValidator()); on { this.searchFilter } }
    }

    prepareResponse("Preparing response")

}).build()