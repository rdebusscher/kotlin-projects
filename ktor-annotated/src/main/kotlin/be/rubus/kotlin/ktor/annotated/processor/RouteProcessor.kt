/**
 * Copyright 2022-2023 Rudy De Busscher (https://www.atbash.be)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rubus.kotlin.ktor.annotated.processor

import be.rubus.kotlin.ktor.annotated.exception.ExceptionResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaType

/**
 * The RouteProcessor is an easy way to process methods that are marked with one of the methods annotations.
 * Use this in your Application class in the routing part as processRoutes(objWithAnnotatedMethods).
 *
 * Adapted from https://gist.github.com/SubSide/76c829a2fa7032372b6b168b273ac654
 */
object RouteProcessor {

    /**
     * Processes all methods in the object(s).
     * This is a convenience method for #process(Routing,Any) so it can easily be used in routing.
     *
     * @param objs   The object(s) to process
     */
    fun Routing.processRoutes(vararg objs: Any) {

        process(this, *objs)
    }

    /**
     * Processes all methods in the objects.
     * All methods that are annotated with any (or multiple) of the annotations will be registered.
     *
     * If your give the annotation a route like /profile/{name} it will fill whatever is filled in {name} in the
     * parameter "name". You can give a parameter the type HttpMethod to get which HTTP method was used. And give a
     * parameter the type ApplicationCall to receive the application call. This can be mandatory to respond to the
     * request.
     *
     * @param routing   The routing object
     * @param objs      The object whose methods should be processed
     */
    fun process(routing: Routing, vararg objs: Any) {
        objs.forEach {
            getRoutePaths(it).forEach { route ->
                // A route path consists of an array of paths, so we need to handle them one by one
                route.paths.forEach { path ->
                    if (route.method == null) {
                        // If the method is null, we want to globally register it instead of registering it to
                        // a certain method.
                        routing.route(path, createRoute(it, route.function))
                    } else {
                        // Otherwise we route it to a single method
                        routing.route(path, route.method, createRoute(it, route.function))
                    }
                }
            }
        }
    }

    /**
     * A method to clean up #process. Does not much special accept the boilerplate code to register the route.
     *
     * @param obj       The object to create the route for
     * @param function  The function that should be called for this route
     * @return          The function that will be executed in the Routes' context
     */
    private fun createRoute(obj: Any, function: KFunction<*>): Route.() -> Unit = {
        handle {
            handleRoute(obj, function, call)
        }
    }

    /**
     * This function handles the route request. It maps the call parameters to the function parameters. After that
     * it calls the function with those parameters in a suspended way.
     *
     * @param obj       The object that the function should be called on
     * @param function  The function that should be called
     * @param call      The requests' ApplicationCall
     */
    private suspend fun handleRoute(
        obj: Any,
        function: KFunction<*>,
        call: ApplicationCall
    ) {
        val requiredMissingParameters = mutableListOf<String>()
        val parameters = function.parameters.associate {
            when {
                it.type.javaType == obj.javaClass -> it to obj
                it.type.javaType == ApplicationCall::class.java -> it to call
                it.type.javaType == HttpMethod::class.java -> it to call.request.local.method
                call.parameters.contains(it.name ?: "") -> it to call.parameters[it.name ?: ""]
                else -> {
                    if (!it.type.isMarkedNullable) {
                        it.name
                            ?: throw UnsupportedOperationException("Un-named parameters on functions are not supported")
                        requiredMissingParameters.add(it.name!!)
                    }
                    val defaultValue = it.findAnnotation<DefaultValue>()?.value?.let { value ->
                        when (it.type.classifier) {
                            String::class -> value
                            Int::class -> value.toInt()
                            Long::class -> value.toLong()
                            Boolean::class -> value.toBoolean()
                            // Add more type handling as needed
                            else -> throw IllegalArgumentException("Unsupported type: ${it.type}")
                        }
                    }
                    it to defaultValue
                }
            }
        }

        if (requiredMissingParameters.isNotEmpty()) {
            call.respond(
                HttpStatusCode.BadRequest,
                ExceptionResponse(
                    "Missing parameter(s) : ${requiredMissingParameters.joinToString(separator = ", ")} ",
                    HttpStatusCode.BadRequest.value
                )
            )
        } else {
            function.callSuspendBy(parameters)
        }
    }

    /**
     * Get RoutePath objects from the "obj" that contains the different routes and to which HTTP method it should map to
     *
     * @param obj   The object it should get the RoutePaths from
     * @return      A list of RoutePaths
     */
    private fun getRoutePaths(obj: Any): List<RoutePaths> {
        return obj::class.declaredMemberFunctions.map { method ->
            method.annotations.mapNotNull {
                when (it) {
                    is GET -> RoutePaths(it.routes, HttpMethod.Get, method)
                    is POST -> RoutePaths(it.routes, HttpMethod.Post, method)
                    is PUT -> RoutePaths(it.routes, HttpMethod.Put, method)
                    is PATCH -> RoutePaths(it.routes, HttpMethod.Patch, method)
                    is DELETE -> RoutePaths(it.routes, HttpMethod.Delete, method)
                    is HEAD -> RoutePaths(it.routes, HttpMethod.Head, method)
                    is OPTIONS -> RoutePaths(it.routes, HttpMethod.Options, method)
                    is ANY -> RoutePaths(it.routes, null, method)
                    else -> null
                }
            }
        }.flatten()
    }

    /**
     * Contains the paths, method and function of a certain route.
     * Will be used to register all the different paths.
     *
     * @param paths     An array of paths that will route to the #function
     * @param method    The HTTP method it should listen to
     * @param function  The function that will be called when this route is visited
     */
    class RoutePaths(
        val paths: Array<out String>,
        val method: HttpMethod?,
        val function: KFunction<*>
    )
}