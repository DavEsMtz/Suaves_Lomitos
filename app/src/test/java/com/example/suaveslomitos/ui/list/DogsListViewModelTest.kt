package com.example.suaveslomitos.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.domain.DataSourceResultState
import com.example.domain.models.DogsCustomErrors
import com.example.domain.models.FetchDogsError
import com.example.domain.usecases.FetchDogsUseCase
import com.example.suaveslomitos.utils.chief
import com.example.suaveslomitos.utils.rex
import com.example.suaveslomitos.utils.spots
import com.example.suaveslomitos.utils.suspendedTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class DogsListViewModelTest {

    @get:Rule
    val testInstantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var dogUseCase: FetchDogsUseCase

    private val viewModel: DogsListViewModel by lazy {
        DogsListViewModel(dogUseCase)
    }

    @Before
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `Test get dogs success`() = suspendedTest {
        // given
        whenever(dogUseCase.invoke()).thenReturn(
            flowOf(
                DataSourceResultState.Success(listOf(rex, spots, chief))
            )
        )

        val expected = DogsListViewState.SuccessDogs(
            listOf(rex, spots, chief)
        )

        viewModel.viewState.test {
            // when
            viewModel.populateDogs()

            // then
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `Test get dogs empty`() = suspendedTest {
        // given
        whenever(dogUseCase.invoke()).thenReturn(
            flowOf(DataSourceResultState.Error(FetchDogsError.EmptyList))
        )

        val expected = DogsListViewState.EmptyDogsList

        viewModel.viewState.test {
            // when
            viewModel.populateDogs()

            // then
            assertEquals(expected, awaitItem())
        }
    }


    @Test
    fun `Test get dogs error`() = suspendedTest {
        // given
        whenever(dogUseCase.invoke()).thenReturn(
            flowOf(DataSourceResultState.Error(DogsCustomErrors.DogsGenericError))
        )

        val expected = DogsListViewState.DogsListError

        viewModel.viewState.test {
            // when
            viewModel.populateDogs()

            assertEquals(expected, awaitItem())
        }
    }
}

