package com.example.data.datasources.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.executors.DogsApiService
import com.example.data.executors.DogsRemoteDataSource
import com.example.data.utils.chief
import com.example.data.utils.rex
import com.example.data.utils.spots
import com.example.data.utils.suspendedTest
import com.example.domain.DataSourceResultState
import com.example.domain.models.DogsCustomErrors
import com.example.domain.models.FetchDogsError
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

class DogsRemoteDataSourceImplTest {

    @get:Rule
    val testInstantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: DogsApiService

    private val remoteDataSource: DogsRemoteDataSource by lazy {
        DogsRemoteDataSourceImpl(apiService)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Test fetch dogs success response`() = suspendedTest {
        // given
        whenever(apiService.getDogs()).thenReturn(Response.success(listOf(rex, spots, chief)))

        val expected = DataSourceResultState.Success(listOf(rex, spots, chief))

        val result = remoteDataSource.getDogs()

        assertEquals(expected, result)
    }

    @Test
    fun `Test fetch dogs failure`() = suspendedTest {
        // given
        whenever(apiService.getDogs()).thenReturn(
            Response.error(403, "Unauthorized".toResponseBody())
        )

        val expected = DataSourceResultState.Error(DogsCustomErrors.DogsGenericError)

        // when
        val result = remoteDataSource.getDogs()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `Test fetch dogs empty list`() = suspendedTest {
        // given
        whenever(apiService.getDogs()).thenReturn(Response.success(listOf()))

        val expected = DataSourceResultState.Error(FetchDogsError.EmptyList)

        // when
        val result = remoteDataSource.getDogs()

        // then
        assertEquals(expected, result)
    }
}

