package com.example.data.executors

import com.example.domain.models.Dog
import retrofit2.Response
import retrofit2.http.GET

internal interface DogsApiService {

    @GET("api/1151549092634943488")
    suspend fun getDogs(): Response<List<Dog>>
}