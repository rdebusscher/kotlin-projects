# MicroStream and Ktor

Example of Integrating MicroStream with a Ktor Web API project.

Some of the functionality

- Data and functionality is contained in specific classes and constructs. trying to maximize the encapsulation.
- Data is kept in mutable Lists within Root but exposed as readonly and a set of functions in 'domain' objects (See `Users` and `Books`)
- Business logic is handled by service classes that are independent from the view.
- A generic Exception handling is applied. Exceptions related to the business rules, are extending from a top-level Exception. And a function is responsible to convert these exceptions to a proper response to the client.
- This generic exception handling also returns JSON as payload ith a code and message.
- The Root object is accessible from everywhere in your code through the `DB` singleton (use `DB.root` and specific domain objects like `DB.root.users`)
- A concurrency solution is in place to protect your data (multiple read, single write, read and write does not happen at same time). It uses the `ReentrantReadWriteLock`, see `Locks`.
