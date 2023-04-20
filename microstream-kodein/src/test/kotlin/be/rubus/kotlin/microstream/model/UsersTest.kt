package be.rubus.kotlin.microstream.model

import one.microstream.experimental.integration.kodein.ContainerAware
import one.microstream.persistence.types.Persister
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UsersTest {

    private val persisterMock: Persister = mock()

    private lateinit var users: Users

    @BeforeEach
    fun setUp() {
        val kodein = DI {
            bind { singleton { persisterMock } }
        }

        ContainerAware.setDI(kodein);
    }

    @Test
    fun addUser() {
        users = Users(mutableListOf())

        val user = User("John Doe", "john@acme.org")
        users.addUser(user)
        Assertions.assertThat(users.all).contains(user)
        verify(persisterMock).store(users.all)
    }
}