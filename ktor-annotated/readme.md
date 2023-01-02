# Alternative to define the routing within Ktor

If you want to associate functions to URLs in a similar way as with JAX-RS in Jakarta EE, you can make use of the `RouteProcessor` code in this project.

## Define methods

Within a class, you can define methods that are annotated to link to the required URL.

```
    @GET("/{name}")
    suspend fun greeting(call: ApplicationCall, name: String) {
    }
```

The name parameter _name_ must match with the URL name used within the annotation.

Optional and nullable parameters are also supported.

```
    @GET("/{name?}")
    suspend fun greeting(call: ApplicationCall, name: String?) {
    }
```

Do note that in the above case, the value of name can be null (as indicated by the type) when calling the endpoint ( localhost:8080/)

The case that the developer makes an error and defines the URL parameter as optional and the function parameter as required, is captured by the `RouteProcessor` code.

```
    @GET("/{name?}")
    suspend fun greeting(call: ApplicationCall, name: String) {
    }
```

This results automatically in a response with status 400 where the response body contains a JSON message indicating that the parameter name is missing (if not specified)

## TODO

- Create `PathParam` annotation so that the variables doesn't need to match.
- Create a converter mechanism so that other types as String are supported as method parameters.