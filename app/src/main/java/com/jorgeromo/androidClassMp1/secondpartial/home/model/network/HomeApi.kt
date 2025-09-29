package com.jorgeromo.androidClassMp1.secondpartial.home.model.network

import com.jorgeromo.androidClassMp1.secondpartial.home.model.dto.RoutinesResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {
    // Usamos el Gist RAW sin fijar commit para siempre obtener la última versión
    @GET("5d64c24ac355f7c309eeb7d3cdc614b3/raw/routines.json")
    suspend fun getRoutines(): Response<RoutinesResponse>
}
