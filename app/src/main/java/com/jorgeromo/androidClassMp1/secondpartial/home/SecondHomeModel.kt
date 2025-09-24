package com.jorgeromo.androidClassMp1.secondpartial.home

data class Rutina(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val musculo: String,
    val imagen: String,
    val duracion: String
)

data class Ejercicio(
    val id: Int,
    val nombre: String,
    val repeticiones: String,
    val categoria: String
)

data class SecondHomeModel(
    val rutinas: List<Rutina> = emptyList(),
    val ejercicios: List<Ejercicio> = emptyList()
)
