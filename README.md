# My Kotlin projects


## microstream

Using MicroStream with Kotlin. The HelloWorld but now in the Kotlin Language.

## microstream-list-db

Demonstrates several concepts
- Using singleton to make root object accessible everywhere
- Using a mutable list internally and expose an immutable list to the rest of the program.

## microstream-ktor

Using MicroStream in combination with Ktor.


## ktor for Jakarta EE developers

See _ktor-basic_ directory for an application that define REST endpoints and support JSON-B and JSON-P alike functionality.

See _ktor-intercept-request_ for 2 ways of intercepting a request (a simple interceptor and a custom application plugin)

See _ktor-annotated_ directory for a way to define 'resource functions' in a way similar to JAX-RS.

```
    @GET("/{name}")
    suspend fun greeting(call: ApplicationCall, name: String) {
    }
```

