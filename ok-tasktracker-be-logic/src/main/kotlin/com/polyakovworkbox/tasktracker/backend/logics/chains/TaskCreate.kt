package com.polyakovworkbox.tasktracker.backend.logics.chains

import com.polyakovworkbox.tasktracker.backend.common.context.BeContext
import com.polyakovworkbox.tasktracker.backend.common.models.general.Operation
import com.polyakovworkbox.tasktracker.backend.logics.workers.chainInit
import com.polyakovworkbox.tasktracker.backend.logics.workers.checkOperation
import com.polyakovworkbox.tasktracker.backend.logics.workers.prepareResponse
import com.polyakovworkbox.tasktracker.backend.logics.workers.taskCreateStub
import ru.otus.otuskotlin.marketplace.common.cor.ICorExec
import ru.otus.otuskotlin.marketplace.common.cor.chain

object TaskCreate: ICorExec<BeContext> by chain<BeContext> ({
    checkOperation("Check that operation is correct", Operation.CREATE)
    chainInit("Init of the chain")

    // validation

    taskCreateStub("Handling stub cases")

    prepareResponse("Preparing response")


}).build()