package com.example.retrofit_1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    //tipo de llamada
    @GET
    suspend fun getDogsByBreeds(@Url url:String):Response<DogsResponse>
}