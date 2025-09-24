package com.jorgeromo.androidClassMp1.secondpartial.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SecondHomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(SecondHomeModel())
    val state: StateFlow<SecondHomeModel> = _state
    
    init {
        // Cargar datos de ejemplo del JSON
        loadSampleData()
    }
    
    private fun loadSampleData() {
        val rutinas = listOf(
            Rutina(
                id = 1,
                nombre = "Rutina Pecho",
                descripcion = "Ejercicios para pecho",
                musculo = "Pecho",
                imagen = "https://res.cloudinary.com/dfrk1camh/image/upload/v1758573449/pecho_vyrxcg.png",
                duracion = "90 min"
            ),
            Rutina(
                id = 2,
                nombre = "Rutina Piernas",
                descripcion = "Ejercicios para piernas",
                musculo = "Piernas",
                imagen = "https://res.cloudinary.com/dfrk1camh/image/upload/v1758573395/piernas_gnmg9i.png",
                duracion = "105 min"
            ),
            Rutina(
                id = 3,
                nombre = "Rutina Espalda",
                descripcion = "Ejercicios para espalda",
                musculo = "Espalda",
                imagen = "https://res.cloudinary.com/dfrk1camh/image/upload/v1758573466/espalda_gzzuae.png",
                duracion = "95 min"
            )
        )
        
        val ejercicios = listOf(
            Ejercicio(
                id = 101,
                nombre = "Press banca",
                repeticiones = "4x10",
                categoria = "Pecho"
            ),
            Ejercicio(
                id = 102,
                nombre = "Sentadilla",
                repeticiones = "4x12",
                categoria = "Piernas"
            ),
            Ejercicio(
                id = 103,
                nombre = "Dominadas",
                repeticiones = "4x8",
                categoria = "Espalda"
            )
        )
        
        _state.value = SecondHomeModel(rutinas = rutinas, ejercicios = ejercicios)
    }
}
