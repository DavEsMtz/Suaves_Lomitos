package com.example.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.datasources.local.dao.entity.DogEntity

@Dao
internal interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dogEntity: DogEntity): Long

    @Query("SELECT * FROM Dogs_Table")
    suspend fun getAllDogs(): List<DogEntity>
}