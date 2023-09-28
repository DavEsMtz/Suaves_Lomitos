package com.example.domain.repositories

import com.example.domain.DataSourceResultState
import com.example.domain.models.Dog
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    suspend fun getDogs(): Flow<DataSourceResultState<List<Dog>>>
}