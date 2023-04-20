# MicroStream and Ktor with Kodein

Example of Integrating MicroStream with a Ktor Web API project using Kodein.

Some of the functionality

- Define the `StorageManager` and `Root` object as Kodein Beans.
- `ContextAware` from MicroStream integration code so that custom classes do not need _DI_ from Kodein as constructor parameter.
- 
- Data and functionality is contained in specific classes and constructs. At construction, no dependency on 'infrastructure' classes by they can be retrieved within the class using `di.instance()`. Trying to maximize the encapsulation.
- Data is kept in mutable Lists within Root but exposed as readonly and a set of functions in 'domain' objects (See `Users` and `Books`)
- Business logic is handled by service classes that are independent from the view.
- A generic Exception handling is applied. Exceptions related to the business rules, are extending from a top-level Exception. And a function is responsible to convert these exceptions to a proper response to the client.
- This generic exception handling also returns JSON as payload ith a code and message.
- The Root object is accessible from everywhere through Kodein functionality.
- A concurrency solution is in place to protect your data (multiple read, single write, read and write does not happen at same time). It uses the `ReentrantReadWriteLock`, see `Locks`.
- Example of testing in combination with Kodein using Mock objects.