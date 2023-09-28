package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.datasources.local.dao.AppDataBase
import com.example.data.datasources.local.dao.DogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
internal class DataBaseModule {

    @Provides
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDataBase =
        Room.databaseBuilder(appContext, AppDataBase::class.java, AppDataBase.DB_NAME).build()

    @Provides
    fun provideDogDao(appDataBase: AppDataBase): DogDao = appDataBase.dogDao

}