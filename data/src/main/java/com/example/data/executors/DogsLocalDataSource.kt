package com.example.data.executors

import com.example.domain.DataSourceResultState
import com.example.domain.models.Dog

internal interface DogsLocalDataSource {
    suspend fun getDogs(): DataSourceResultState<List<Dog>>
    suspend fun saveDogs(dogs: List<Dog>)
}