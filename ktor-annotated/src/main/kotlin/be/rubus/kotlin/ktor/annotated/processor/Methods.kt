package be.rubus.kotlin.ktor.annotated.processor

// See https://gist.github.com/SubSide/76c829a2fa7032372b6b168b273ac654

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class GET(vararg val routes: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class POST(vararg val routes: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class PUT(vararg val routes: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class PATCH(vararg val routes: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DELETE(vararg val routes: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class HEAD(vararg val routes: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class OPTIONS(vararg val routes: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ANY(vararg val routes: String)
