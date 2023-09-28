package com.example.domain.usecases

import com.example.domain.DataSourceResultState
import com.example.domain.models.Dog
import com.example.domain.repositories.DogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDogsUseCase @Inject constructor(private val repository: DogsRepository) {

    suspend operator fun invoke(): Flow<DataSourceResultState<List<Dog>>> {
        return repository.getDogs()
    }
}