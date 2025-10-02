package com.jorgeromo.androidClassMp1.secondpartial.home.model.dto

// DTOs que reflejan exactamente el JSON del Gist (no embebemos el JSON en código)

data class RoutinesResponse(
    val rutinas: List<RutinaDto> = emptyList(),
    val ejercicios: List<EjercicioDto> = emptyList()
)

data class RutinaDto(
    val id: Int,
    val nombre: String? = null,
    val descripcion: String? = null,
    val musculo: String? = null,
    val imagen: String? = null,
    val duracion: String? = null
)

data class EjercicioDto(
    val id: Int,
    val nombre: String? = null,
    val repeticiones: String? = null,
    val categoria: String? = null
)
