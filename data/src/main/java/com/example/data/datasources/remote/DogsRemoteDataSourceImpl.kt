package com.example.data.datasources.remote

import com.example.data.executors.DogsApiService
import com.example.data.executors.DogsRemoteDataSource
import com.example.domain.DataSourceResultState
import com.example.domain.models.Dog
import com.example.domain.models.FetchDogsError
import com.example.domain.models.DogsCustomErrors

internal class DogsRemoteDataSourceImpl(private val apiService: DogsApiService) :
    DogsRemoteDataSource {

    override suspend fun getDogs(): DataSourceResultState<List<Dog>> {
        return try {
            val response = apiService.getDogs()
            if (response.isSuccessful) {
                response.body()?.let { dogs ->
                    if (dogs.isNotEmpty()) DataSourceResultState.Success(dogs)
                    else DataSourceResultState.Error(FetchDogsError.EmptyList)
                } ?: DataSourceResultState.Error(FetchDogsError.EmptyList)
            } else {
                // TODO: Handle malformed or some error
                DataSourceResultState.Error(DogsCustomErrors.DogsGenericError)
            }
        } catch (e: Exception) {
            DataSourceResultState.Error(DogsCustomErrors.DogsGenericError)
        }
    }
}