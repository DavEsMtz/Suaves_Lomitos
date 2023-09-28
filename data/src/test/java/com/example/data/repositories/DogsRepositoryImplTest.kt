package com.example.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.Event
import app.cash.turbine.test
import com.example.data.executors.DogsLocalDataSource
import com.example.data.executors.DogsRemoteDataSource
import com.example.data.utils.chief
import com.example.data.utils.rex
import com.example.data.utils.spots
import com.example.data.utils.suspendedTest
import com.example.domain.DataSourceResultState
import com.example.domain.models.DogsCustomErrors
import com.example.domain.models.FetchDogsError
import com.example.domain.repositories.DogsRepository
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class DogsRepositoryImplTest {

    @get:Rule
    val testInstantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteDataSource: DogsRemoteDataSource

    @Mock
    private lateinit var localDataSource: DogsLocalDataSource

    private val repository: DogsRepository by lazy {
        DogsRepositoryImpl(
            localDataSource, remoteDataSource
        )
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Test fetch dogs from remote only successfully`() = suspendedTest {
        // given
        whenever(remoteDataSource.getDogs()).thenReturn(
            DataSourceResultState.Success(listOf(rex, spots, chief))
        )
        whenever(localDataSource.getDogs()).thenReturn(DataSourceResultState.Error(FetchDogsError.EmptyList))

        val expected = DataSourceResultState.Success(listOf(rex, spots, chief))

        // when
        repository.getDogs().test {
            val result = cancelAndConsumeRemainingEvents()

            // then
            with(result) {
                assertTrue(isNotEmpty())
                assertTrue(contains(Event.Item(expected)))
            }
        }
    }

    @Test
    fun `Test fetch dogs from local only successfully`() = suspendedTest {
        // given
        whenever(localDataSource.getDogs()).thenReturn(
            DataSourceResultState.Success(listOf(rex, spots, chief))
        )
        whenever(remoteDataSource.getDogs()).thenReturn(DataSourceResultState.Error(FetchDogsError.EmptyList))

        val expected = DataSourceResultState.Success(listOf(rex, spots, chief))

        // when
        repository.getDogs().test {
            val result = cancelAndConsumeRemainingEvents()

            // then
            with(result) {
                assertTrue(isNotEmpty())
                assertTrue(contains(Event.Item(expected)))
            }
        }
    }

    @Test
    fun `Test fetch dogs from local and remote successfully`() = suspendedTest {
        // given
        whenever(localDataSource.getDogs()).thenReturn(
            DataSourceResultState.Success(listOf(rex))
        )
        whenever(remoteDataSource.getDogs()).thenReturn(
            DataSourceResultState.Success(listOf(rex, spots, chief))
        )

        val expected = DataSourceResultState.Success(listOf(rex, spots, chief))

        // when
        repository.getDogs().test {
            val result = cancelAndConsumeRemainingEvents()

            // then
            with(result) {
                assertTrue(isNotEmpty())
                assertTrue(contains(Event.Item(expected)))
            }
        }
    }

    @Test
    fun `Test fetch dogs with no changes`() = suspendedTest {
        // given
        whenever(localDataSource.getDogs()).thenReturn(
            DataSourceResultState.Success(listOf(rex, spots, chief))
        )
        whenever(remoteDataSource.getDogs()).thenReturn(
            DataSourceResultState.Success(listOf(rex, spots, chief))
        )

        val expected = DataSourceResultState.Success(listOf(rex, spots, chief))

        // when
        repository.getDogs().test {
            val result = cancelAndConsumeRemainingEvents()

            // then
            with(result) {
                assertTrue(isNotEmpty())
                assertTrue(contains(Event.Item(expected)))
            }
        }
    }

    @Test
    fun `Test fetch dogs from local and remote failure`() = suspendedTest {
        // given
        whenever(localDataSource.getDogs()).thenReturn(DataSourceResultState.Error(FetchDogsError.EmptyList))
        whenever(remoteDataSource.getDogs()).thenReturn(DataSourceResultState.Error(DogsCustomErrors.DogsGenericError))

        val expected =
            DataSourceResultState.Error(FetchDogsError.EmptyList)

        // when
        repository.getDogs().test {
            val result = cancelAndConsumeRemainingEvents()

            // then
            with(result) {
                assertTrue(isNotEmpty())
                assertTrue(contains(Event.Item(expected)))
            }
        }
    }
}