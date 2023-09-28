package com.example.domain.models

sealed class DogsCustomErrors : Throwable() {
    object DogsGenericError : DogsCustomErrors()
}

sealed class FetchDogsError : DogsCustomErrors() {
    object EmptyList : FetchDogsError()
}