package com.example.data.datasources.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.datasources.local.dao.entity.DogEntity


@Database(entities = [DogEntity::class], version = 1, exportSchema = false)
internal abstract class AppDataBase : RoomDatabase() {

    abstract val dogDao: DogDao

    companion object {
        const val DB_NAME = "suaves_bases_de_datos.db"
    }

}