package com.example.data.datasources.local.dao.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dogs_Table")
internal class DogEntity(
    @PrimaryKey(autoGenerate = true) var entityId: Int?,
    val dogName: String,
    val description: String,
    val age: Int,
    val imageUrl: String
)