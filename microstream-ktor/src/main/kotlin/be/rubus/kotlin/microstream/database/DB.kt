package be.rubus.kotlin.microstream.database

import be.rubus.kotlin.microstream.model.Book
import be.rubus.kotlin.microstream.model.User
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration
import one.microstream.storage.types.StorageManager

/**
 * Singleton to access the Root object from anywhere {@code DB.root}. This
 * Root is initialized from storage or created and stored as root object when no storage
 * created previously.
 */
object DB {
    private val storageManager: StorageManager by lazy {
        // Lazy initialize the StorageManager.
        startAndInitialiseDatabase()
    }

    private fun startAndInitialiseDatabase(): StorageManager {
        val storageManager: StorageManager = createStorageManager()
        val root = storageManager.root() as Root?
        // initialize root if needed
        initData(storageManager, root)
        return storageManager
    }

    private fun createStorageManager(): StorageManager {
        // requires  microstream-storage-embedded-configuration dependency
        return EmbeddedStorageConfiguration.Builder()
                // FIXME Use external configuration
            .setStorageDirectory("microstream-data")
            .setChannelCount(1)
            .createEmbeddedStorageFoundation()

            .createEmbeddedStorageManager()
            .start()
    }

    private fun initData(storageManager: StorageManager, root: Root?) {
        if (root == null) {
            // This also 'registers' it as root
            val newRoot = Root(storageManager)
            init(newRoot, storageManager)
        }
    }

    private fun init(root: Root, storageManager: StorageManager) {
        val johnDoe = User("John Doe", "john.doe@acme.org")
        val janeDoe = User("Jane Doe", "jane.doe@acme.org")

        root.users.addUser(johnDoe)
        root.users.addUser(janeDoe)

        addBook(root, "9780140434132", "Northanger Abbey", "Austen, Jane", 1814)
        addBook(root, "9780007148387", "War and Peace", "Tolstoy, Leo", 1865)
        addBook(root, "9780141182490", "Mrs. Dalloway", "Woolf, Virginia", 1925)
        addBook(root, "9780312243029", "The Hours", "Cunnningham, Michael", 1999)
        addBook(root, "9780141321097", "Huckleberry Finn", "Twain, Mark", 1865)
        addBook(root, "9780141439723", "Bleak House", "Dickens, Charles", 1870)
        addBook(root, "9780520235755", "The adventures of Tom Sawyer", "Twain, Mark", 1862)
        addBook(root, "9780156030410", "A Room of One's Own", "Woolf, Virginia", 1922)
        addBook(root, "9780140707342", "Hamlet, Prince of Denmark", "Shakespeare", 1603)
        addBook(root, "9780395647400", "Lord of the Rings", "Tolkien, J.R.", 1937)
        val annaKarenina: Book = addBook(root, "9780679783305", "Anna Karenina", "Tolstoy, Leo", 1875)
        janeDoe.addBook(annaKarenina, storageManager)
        val book: Book = addBook(root, "9780060114183", "One Hundred Years of Solitude", "Marquez", 1967)
        janeDoe.addBook(book, storageManager)
        val harryPotter: Book = addBook(root, "9780747532743", "Harry Potter", "Rowling, J.K.", 2000)
        johnDoe.addBook(harryPotter, storageManager)
    }

    private fun addBook(root: Root, isbn: String, name: String, author: String, year: Int): Book {
        val result = Book(isbn, name, author, year)
        root.books.add(result)
        return result
    }

    val root: Root
        get() = storageManager.root() as Root
}