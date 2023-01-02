package be.rubus.kotlin.ktor.annotated.resources

import be.rubus.kotlin.ktor.annotated.exception.ExceptionResponse
import be.rubus.kotlin.ktor.annotated.processor.GET
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class HelloResource {

    @GET("/")
    suspend fun helloWorld(call: ApplicationCall) {
        // val uri = call.request.uri  complete uri of request
        call.respondText("Hello World!")
    }

    @GET("/{name}")
    suspend fun greeting(call: ApplicationCall, name: String) {
        val language = call.request.queryParameters["language"] ?: "en"
        call.respondText("Hello $name with language $language")
    }

}