package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskCreateStub
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskDeleteStub
import ru.otus.otuskotlin.marketplace.common.cor.ICorExec
import ru.otus.otuskotlin.marketplace.common.cor.chain

object TaskDelete: ICorExec<BeContext> by chain<BeContext> ({
    checkOperation("Check that operation is correct", Operation.DELETE)
    chainInit("Init of the chain")

    // validation

    taskDeleteStub("Handling stub cases")

    prepareResponse("Preparing response")


}).build()