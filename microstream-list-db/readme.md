# MicroStream Kotlin immutable list example

Exposing your data immutable and have functions to modify it has several advantages

- Data cannot be modified outside your 'class'.  It forces the developer to use your functions to perform this task.
- Within these functions, you can perform whatever is needed, like storing changes through MicroStream.
- Concurrency measures can be implemented in these functions so that data access is properly handled in multi-threaded environments.

Kotlin has immutable and mutable collection types, like `List` and `MutableList`.  And writing a class that keeps a mutable list internally and exposes it as immutable list externally is very straight forward in Kotlin.

```
    // A mutable list that is kept internal
    private val items_ = mutableListOf<String>()

    // Expose a read-only version
    val items: List<String>
        get() = items_

```

See `Data` file for the definition and `Demo` for using it.

# MicroStream _database_

How can we make the Root object that is considered as starting point of the object graph that is our database, accessible through-out our application?

Kotlin has a useful concept for this situation, the singleton that can specified by defining an `object`.

Have a look at the `DB` file that implements a possible solution.  Some aspects of this solution

- The Root object is accessible from anywhere by calling `DB.root`.
- The MicroStream `StorageManager` is initialised lazily, only when the root object is accessed for the first time. (using the `by lazy` construct of Kotlin)
- The `StorageManager` is _'injected'_ into the root object when it is loaded from the storage by MicroStream (due to the _transient Persister_ construct). So the MicroStream functionality is always available within root, either when constructed in code or created by MicroStream.
- The initialisation within `DB` singleton assures that there is a 'default' root made available when you run the program for the first time (no storage yet and thus a new instance of `Root` is set as object graph root)
- The example makes use of the internal mutable list and immutable exposure to the outside.

Run the following programs

- `Step1` -> Creates a storage with 2 entries as data items.
- `ShowData` -> Prints out the contents of the data.
- `Step2` -> Add additional item to the database/storage
- `ShowData` -> Prints out updated contents now. 