package com.example.data.executors

import com.example.domain.DataSourceResultState
import com.example.domain.models.Dog

internal interface DogsRemoteDataSource {
    suspend fun getDogs(): DataSourceResultState<List<Dog>>
}