package be.rubus.kotlin.microstream.service

import be.rubus.kotlin.microstream.database.Locks
import be.rubus.kotlin.microstream.database.Root
import one.microstream.experimental.integration.kodein.ContainerAware
import org.kodein.di.instance

abstract class AbstractService protected constructor() : ContainerAware() {

    protected val locks: Locks = Locks

    protected val root: Root by di.instance()

}