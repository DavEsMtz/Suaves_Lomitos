package com.example.domain.models

data class Dog(
    val dogName: String,
    val description: String,
    val age: Int,
    val image: String
) {
    var dogId: Int? = null
}