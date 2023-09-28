package com.example.data.datasources.local

import com.example.data.datasources.local.dao.DogDao
import com.example.data.executors.DogsLocalDataSource
import com.example.data.mappers.toEntity
import com.example.data.mappers.toModel
import com.example.domain.DataSourceResultState
import com.example.domain.models.Dog
import com.example.domain.models.FetchDogsError

internal class DogsLocalDataSourceImpl(private val dogDao: DogDao) : DogsLocalDataSource {
    override suspend fun getDogs(): DataSourceResultState<List<Dog>> {
        val dogsList = dogDao.getAllDogs()
        return if (dogsList.isEmpty()) DataSourceResultState.Error(FetchDogsError.EmptyList)
        else DataSourceResultState.Success(dogsList.map { it.toModel() })
    }

    override suspend fun saveDogs(dogs: List<Dog>) {
        dogs.forEach { dog ->
            dogDao.insert(dog.toEntity())
        }
    }
}