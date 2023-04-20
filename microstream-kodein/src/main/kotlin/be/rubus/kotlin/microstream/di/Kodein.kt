package be.rubus.kotlin.microstream.di

import be.rubus.kotlin.microstream.database.DB
import be.rubus.kotlin.microstream.database.InitDatabase
import be.rubus.kotlin.microstream.database.Root
import be.rubus.kotlin.microstream.service.BookService
import be.rubus.kotlin.microstream.service.UserBookService
import be.rubus.kotlin.microstream.service.UserService
import io.ktor.server.application.*
import one.microstream.experimental.integration.kodein.ContainerAware
import one.microstream.experimental.integration.kodein.MicrostreamKodein
import org.kodein.di.bind
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.kodein.di.singleton

fun Application.configureDI() {

    di {
        bind { singleton { InitDatabase() } }  // Define Intialization as Kodein Bean. Picked up by bindRoot
        bind { singleton { DB.storageManager.value } }  // .value as we need the instance and not the 'Kotlin Lazy'
        MicrostreamKodein.bindRoot(this, Root::class, DB.storageManager)  // Expose Root as Kodein bean

        bind { singleton { UserService() } }
        bind { singleton { UserBookService() } }
        bind { singleton { BookService() } }
    }

    // Make DI available through our ContainerAware helper.
    ContainerAware.setDI(closestDI())

}
