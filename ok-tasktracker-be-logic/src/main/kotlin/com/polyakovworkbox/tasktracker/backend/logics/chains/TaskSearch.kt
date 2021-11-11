package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.common.models.task.filter.SearchFilter
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainPermissions
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.repo.repoSearch
import com.polyakovworkbox.tasktracker.backend.logics.workers.selectDB
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskSearchStub
import com.polyakovworkbox.tasktracker.common.cor.ICorExec
import com.polyakovworkbox.tasktracker.common.cor.chain
import com.polyakovworkbox.tasktracker.common.cor.validation
import com.polyakovworkbox.tasktracker.common.cor.worker
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

    chainPermissions("Computing user permissions")
    chain {
        title = "Preparing search request"
        description = "Adding restrictions according to user permissions"
        on { status == CorStatus.RUNNING }
        worker {
            title = "Определение типа поиска"
            description = title
            handle {
                searchFilter.searchTypes = listOf(
                    MpSearchTypes.OWN.takeIf { chainPermissions.contains(MpUserPermissions.SEARCH_OWN) },
                    MpSearchTypes.PUBLIC.takeIf { chainPermissions.contains(MpUserPermissions.SEARCH_PUBLIC) },
                    MpSearchTypes.REGISTERED.takeIf { chainPermissions.contains(MpUserPermissions.SEARCH_REGISTERED) },
                ).filterNotNull().toMutableSet()
            }
        }
        worker("Копируем все поля бизнес-поиска") {
            dbFilter.dealSide = requestFilter.dealSide
            dbFilter.ownerId = requestFilter.ownerId
            dbFilter.searchStr = requestFilter.searchStr
        }
    }

    repoSearch("Search task according to the filter arguments in DB")

    prepareResponse("Preparing response")

}).build()