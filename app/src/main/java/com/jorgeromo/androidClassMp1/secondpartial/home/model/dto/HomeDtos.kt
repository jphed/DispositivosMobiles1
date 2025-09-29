package com.jorgeromo.androidClassMp1.secondpartial.home.model.dto

// DTOs que reflejan exactamente el JSON del Gist (no embebemos el JSON en código)

data class RoutinesResponse(
    val rutinas: List<RutinaDto> = emptyList(),
    val ejercicios: List<EjercicioDto> = emptyList()
)

data class RutinaDto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val musculo: String,
    val imagen: String,
    val duracion: String
)

data class EjercicioDto(
    val id: Int,
    val nombre: String,
    val repeticiones: String,
    val categoria: String
)
