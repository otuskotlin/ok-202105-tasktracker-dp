package com.polyakovworkbox.tasktracker.springapp.utils
/*
import com.polyakovworkbox.otuskotlin.tasktracker.transport.openapi.task.models.BaseMessage
import com.polyakovworkbox.tasktracker.backend.common.context.BeContext

suspend inline fun <reified T : BaseMessage, reified U : BaseMessage> ApplicationCall.handleRoute(
    logId: String,
    logger: LogWrapper,
    crossinline block: suspend BeContext.(T) -> U
) {
    logger.doWithLogging(logId) {
        val request = receive<BaseMessage>() as T
        val context = BeContext(
            startTime = Instant.now(),
            principal = principal<JWTPrincipal>().toModel()
        )
        try {
            val response = context.block(request)
            respond(response)
        } catch (e: Exception) {
            context.addError(e)
            val response = context.block(request)
            respond(response)
        }
    }
}*/
