package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.CorStatus
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.task.OwnerId
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.backendPermissions
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.repo.repoSearch
import com.polyakovworkbox.tasktracker.backend.logics.workers.selectDB
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskSearchStub
import com.polyakovworkbox.tasktracker.common.cor.ICorExec
import com.polyakovworkbox.tasktracker.common.cor.chain
import com.polyakovworkbox.tasktracker.common.cor.validation
import com.polyakovworkbox.tasktracker.common.handlers.worker
import com.polyakovworkbox.tasktracker.validators.DueTimeWithoutEqualityModeValidator
import com.polyakovworkbox.tasktracker.validators.PercentWithoutEqualityModeValidator

object TaskSearch: ICorExec<BeContext> by chain<BeContext> ({

    checkOperation("Check that operation is correct", Operation.SEARCH)
    chainInit("Init of the chain")

    selectDB("selection between prod DB and stub DB")

    taskSearchStub("Handling stub cases")

    validation {
        validate<SearchFilter> { validator(PercentWithoutEqualityModeValidator()); on { this.searchFilter } }
        validate<SearchFilter> { validator(DueTimeWithoutEqualityModeValidator()); on { this.searchFilter } }
    }

    backendPermissions("Computing user permissions")

    chain<BeContext> {
        title = "Preparing search request"
        description = "Adding restrictions according to user permissions"
        on { corStatus == CorStatus.RUNNING }
        worker {
            title = "Adding mandatory owner id to search filter if it is not set yet"
            handle {
                searchFilter.ownerId = OwnerId(principal.id)
            }
        }
    }

    repoSearch("Search task according to the filter arguments in DB")

    prepareResponse("Preparing response")

}).build()