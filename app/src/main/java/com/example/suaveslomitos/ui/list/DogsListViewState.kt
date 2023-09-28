package com.example.suaveslomitos.ui.list

import com.example.domain.models.Dog

sealed class DogsListViewState {
    object LoadingDogs : DogsListViewState()
    data class SuccessDogs(val dogs: List<Dog>) : DogsListViewState()
    object EmptyDogsList : DogsListViewState()
    object DogsListError : DogsListViewState()
}
