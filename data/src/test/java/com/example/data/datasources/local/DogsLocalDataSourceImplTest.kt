package com.example.data.datasources.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.datasources.local.dao.DogDao
import com.example.data.executors.DogsLocalDataSource
import com.example.data.mappers.toEntity
import com.example.data.utils.chief
import com.example.data.utils.rex
import com.example.data.utils.spots
import com.example.data.utils.suspendedTest
import com.example.domain.DataSourceResultState
import com.example.domain.models.FetchDogsError
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class DogsLocalDataSourceImplTest {

    @get:Rule
    val testInstantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var dogDao: DogDao

    private val localDataSource: DogsLocalDataSource by lazy {
        DogsLocalDataSourceImpl(dogDao)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Test get local dogs successfully`() = suspendedTest {
        // given
        whenever(dogDao.getAllDogs())
            .thenReturn(listOf(rex, spots, chief).map { dog -> dog.toEntity() })

        val expected = DataSourceResultState.Success(listOf(rex, spots, chief))

        // when
        val result = localDataSource.getDogs()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun `Test get local dogs empty list`() = suspendedTest {
        // given
        whenever(dogDao.getAllDogs()).thenReturn(emptyList())

        val expected = DataSourceResultState.Error(FetchDogsError.EmptyList)

        // when
        val result = localDataSource.getDogs()

        // then
        assertEquals(expected, result)
    }
}