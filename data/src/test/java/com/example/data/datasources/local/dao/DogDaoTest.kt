package com.example.data.datasources.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.datasources.local.dao.entity.DogEntity
import com.example.data.mappers.toEntity
import com.example.data.utils.chief
import com.example.data.utils.rex
import com.example.data.utils.spots
import com.example.data.utils.suspendedTest
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class DogDaoTest {

    @get:Rule
    val testInstantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var dogDao: DogDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `Test insert dog`() = suspendedTest {
        val dogEntity: DogEntity = rex.toEntity()

        whenever(dogDao.insert(dogEntity)).thenReturn(1L)

        val insertedDog = dogDao.insert(dogEntity)

        assertNotNull(insertedDog)
    }

    @Test
    fun `Test get all dogs successfully`() = suspendedTest {
        // given
        whenever(dogDao.getAllDogs())
            .thenReturn(listOf(rex.toEntity(), spots.toEntity(), chief.toEntity()))

        // when
        val result = dogDao.getAllDogs()

        // then
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun `Test all dogs empty`() = suspendedTest {
        // given
        whenever(dogDao.getAllDogs())
            .thenReturn(emptyList())

        // when
        val result = dogDao.getAllDogs()

        // then
        assertTrue(result.isEmpty())
    }
}