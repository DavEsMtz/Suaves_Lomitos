package com.example.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.Event
import app.cash.turbine.test
import com.example.domain.DataSourceResultState
import com.example.domain.models.Dog
import com.example.domain.models.FetchDogsError
import com.example.domain.repositories.DogsRepository
import com.example.domain.utils.suspendedTest
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class FetchDogsUseCaseTest {

    @get:Rule
    val testInstantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: DogsRepository

    private val dogsUseCase: FetchDogsUseCase by lazy {
        FetchDogsUseCase(repository)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test fetch dogs successfully`() = suspendedTest {
        // given
        whenever(repository.getDogs()).thenReturn(
            flowOf(DataSourceResultState.Success(listOf(rex, chief, spots)))
        )

        val expected = DataSourceResultState.Success(listOf(rex, chief, spots))

        // when
        dogsUseCase.invoke().test {
            val result = cancelAndConsumeRemainingEvents()

            // then
            with(result) {
                assertFalse(isEmpty())
                assertTrue(contains(Event.Item(expected)))
            }
        }
    }

    @Test
    fun `test fetch dogs empty list`() = suspendedTest {
        // given
        whenever(repository.getDogs()).thenReturn(
            flowOf(DataSourceResultState.Error(FetchDogsError.EmptyList))
        )

        val expected = DataSourceResultState.Error(FetchDogsError.EmptyList)

        // when
        dogsUseCase.invoke().test {
            val result = cancelAndConsumeRemainingEvents()

            // then
            with(result) {
                assertFalse(isEmpty())
                assertTrue(contains(Event.Item(expected)))
            }
        }
    }
}

/** TEST DATA **/

val rex = Dog(
    "Rex",
    description = "He is much more passive and is the first to suggest to rescue and not eat The Little Pilot",
    age = 5,
    image = "https://static.wikia.nocookie.net/isle-of-dogs/images/a/af/Rex.jpg/revision/latest/scale-to-width-down/666?cb=20180625001634"
)

val spots = Dog(
    "Spots",
    description = "Is the brother of Chief and are also a former guard dog for Mayor Kobayashi's ward",
    age = 2,
    image = "https://static.wikia.nocookie.net/isle-of-dogs/images/6/6b/Spots.jpg/revision/latest/scale-to-width-down/666?cb=20180624191101"
)

val chief = Dog(
    "Chief",
    description = "He is a leader of a pack of dogs",
    age = 8,
    image = "https://static.wikia.nocookie.net/isle-of-dogs/images/1/1d/Chief-0.jpg/revision/latest/scale-to-width-down/666?cb=20180624184431"
)
