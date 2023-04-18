package be.rubus.kotlin.microstream.routes

import be.rubus.kotlin.microstream.exception.types.HasMissingParameterException
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

/**
 * Helper class to extract values from the URL path.
 */
object URLParameters {
    fun extractUserId(context: PipelineContext<Unit, ApplicationCall>): String {
        return context.call.parameters["userId"] ?: throw HasMissingParameterException("Missing 'userId' in URL")
    }
    fun extractIsbn(context: PipelineContext<Unit, ApplicationCall>): String {
        return context.call.parameters["isbn"] ?: throw HasMissingParameterException("Missing 'isbn' in URL")
    }
    fun extractBookId(context: PipelineContext<Unit, ApplicationCall>): String {
        return context.call.parameters["bookId"] ?: throw HasMissingParameterException("Missing 'bookId' in URL")
    }
    fun extractEmail(context: PipelineContext<Unit, ApplicationCall>): String {
        return context.call.parameters["email"] ?: throw HasMissingParameterException("Missing 'email' in URL")
    }
}