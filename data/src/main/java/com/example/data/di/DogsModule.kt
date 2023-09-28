package com.example.data.di

import com.example.data.datasources.remote.DogsRemoteDataSourceImpl
import com.example.data.datasources.local.dao.DogDao
import com.example.data.datasources.local.DogsLocalDataSourceImpl
import com.example.data.executors.DogsApiService
import com.example.data.executors.DogsLocalDataSource
import com.example.data.executors.DogsRemoteDataSource
import com.example.data.repositories.DogsRepositoryImpl
import com.example.domain.repositories.DogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal class DogsModule {

    @Provides
    fun provideFetchDogsApiService(retrofit: Retrofit): DogsApiService =
        retrofit.create(DogsApiService::class.java)

    @Provides
    fun provideDogsRemoteDataSource(apiService: DogsApiService): DogsRemoteDataSource =
        DogsRemoteDataSourceImpl(apiService)

    @Provides
    fun provideDogsLocalDataSource(dogDao: DogDao): DogsLocalDataSource =
        DogsLocalDataSourceImpl(dogDao)

    @Provides
    fun provideDogsRepository(
        localDataSource: DogsLocalDataSource,
        remoteDataSource: DogsRemoteDataSource
    ): DogsRepository = DogsRepositoryImpl(localDataSource, remoteDataSource)
}