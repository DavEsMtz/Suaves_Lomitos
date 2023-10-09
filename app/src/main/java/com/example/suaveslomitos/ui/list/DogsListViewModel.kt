package com.example.suaveslomitos.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DataSourceResultState
import com.example.domain.models.FetchDogsError
import com.example.domain.usecases.FetchDogsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DogsListViewModel @Inject constructor(private val useCase: FetchDogsUseCase) : ViewModel() {

    private val _viewState =
        MutableStateFlow<DogsListViewState>(DogsListViewState.LoadingDogs)
    val viewState get() = _viewState

    init {
        fetchDogs()
    }

    fun populateDogs() {
        fetchDogs()
    }

    private fun fetchDogs() {
        viewModelScope.launch {
            useCase.invoke().collect { dogsResultState ->
                when (dogsResultState) {
                    is DataSourceResultState.Error -> {
                        handleError(dogsResultState.error)
                    }

                    is DataSourceResultState.Success -> {
                        _viewState.emit(DogsListViewState.SuccessDogs(dogsResultState.data))
                    }

                    else -> {}
                }
            }
        }
    }


    private fun handleError(error: Throwable) {
        viewModelScope.launch {
            when (error) {
                is FetchDogsError.EmptyList -> {
                    _viewState.emit(DogsListViewState.EmptyDogsList)
                }

                else -> {
                    _viewState.emit(DogsListViewState.DogsListError)
                }
            }
        }

    }
}