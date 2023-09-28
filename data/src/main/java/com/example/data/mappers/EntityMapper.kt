package com.example.data.mappers

import com.example.data.datasources.local.dao.entity.DogEntity
import com.example.domain.models.Dog

internal fun Dog.toEntity() =
    DogEntity(
        entityId = null,
        dogName = dogName,
        description = description,
        age = age,
        imageUrl = image
    )

internal fun DogEntity.toModel() =
    Dog(
        dogName = dogName,
        description = description,
        age = age,
        image = imageUrl
    ).apply { this.dogId = entityId }