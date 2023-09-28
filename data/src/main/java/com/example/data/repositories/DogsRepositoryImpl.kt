package com.example.data.repositories

import com.example.data.executors.DogsLocalDataSource
import com.example.data.executors.DogsRemoteDataSource
import com.example.domain.DataSourceResultState
import com.example.domain.models.Dog
import com.example.domain.models.FetchDogsError
import com.example.domain.repositories.DogsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

internal class DogsRepositoryImpl(
    private val localDataSource: DogsLocalDataSource,
    private val remoteDataSource: DogsRemoteDataSource
) : DogsRepository {

    override suspend fun getDogs(): Flow<DataSourceResultState<List<Dog>>> {
        return flowOf(remoteDataSource.getDogs())
            .combine(flowOf(localDataSource.getDogs())) { remoteResult: DataSourceResultState<List<Dog>>, localResult: DataSourceResultState<List<Dog>> ->
                when {
                    remoteResult is DataSourceResultState.Success &&
                            localResult is DataSourceResultState.Success -> {
                        val remoteList = remoteResult.data.toSet()
                        val localList = localResult.data.toMutableSet()
                        if (remoteList != localList) {
                            /* Here we could handle different approaches for this, like
                            delete local elements that are not on remote response...
                            At the moment only merges both lists if exists differences*/
                            localList.addAll(remoteList)
                            localDataSource.saveDogs(localList.toList())
                            DataSourceResultState.Success(localList.toList())
                        } else {
                            DataSourceResultState.Success(localList.toList())
                        }
                    }

                    remoteResult is DataSourceResultState.Success &&
                            localResult is DataSourceResultState.Error -> {
                        localDataSource.saveDogs(remoteResult.data)
                        remoteResult
                    }

                    localResult is DataSourceResultState.Success &&
                            remoteResult is DataSourceResultState.Error -> {
                        localResult
                    }

                    else -> {
                        /* Here we could work with multiple error scenarios to be handled by viewModel
                        from either api response or local DB, unauthorized user, token expired, etc...
                        for this time just an EmptyListError will be exposed */
                        // remoteResult
                        DataSourceResultState.Error(FetchDogsError.EmptyList)
                    }
                }
            }
    }
}