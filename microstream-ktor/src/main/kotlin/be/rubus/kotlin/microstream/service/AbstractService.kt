package be.rubus.kotlin.microstream.service

import be.rubus.kotlin.microstream.database.Locks
import be.rubus.kotlin.microstream.database.Root
import be.rubus.kotlin.microstream.database.DB

abstract class AbstractService protected constructor() {

    protected val locks: Locks = Locks

    protected val root: Root = DB.root

}