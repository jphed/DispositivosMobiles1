package com.jorgeromo.androidClassMp1.secondpartial.home.repository

import com.jorgeromo.androidClassMp1.secondpartial.home.model.dto.RoutinesResponse
import com.jorgeromo.androidClassMp1.secondpartial.home.model.network.HomeApi
import java.io.IOException

class HomeRepository(private val api: HomeApi) {
    suspend fun getRoutines(): Result<RoutinesResponse> {
        return try {
            val url = "https://gist.githubusercontent.com/YajahiraPP/5d64c24ac355f7c309eeb7d3cdc614b3/raw/routines.json"
            println("[DEBUG] HomeRepository: URL de petición: $url")
            val resp = api.getRoutines()
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Respuesta vacía del servidor (HTTP ${resp.code()})"))
                }
            } else {
                val msg = resp.errorBody()?.string().orEmpty()
                val errorMsg = "HTTP ${resp.code()} - ${resp.message()}\n$msg"
                Result.failure(Exception(errorMsg.ifBlank { "Error al obtener datos de Home" }))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Sin conexión. Verifica tu red. (${e.localizedMessage})"))
        } catch (e: Exception) {
            Result.failure(Exception("Error inesperado: ${e.localizedMessage}"))
        }
    }
}
