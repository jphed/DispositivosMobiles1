package com.jorgeromo.androidClassMp1.secondpartial.home.viewmodel

import com.jorgeromo.androidClassMp1.secondpartial.home.model.dto.EjercicioDto
import com.jorgeromo.androidClassMp1.secondpartial.home.model.dto.RutinaDto

data class HomeUiState(
    val rutinas: List<RutinaDto> = emptyList(),
    val ejercicios: List<EjercicioDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
