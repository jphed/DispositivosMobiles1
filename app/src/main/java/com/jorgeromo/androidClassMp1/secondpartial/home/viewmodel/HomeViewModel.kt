package com.jorgeromo.androidClassMp1.secondpartial.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorgeromo.androidClassMp1.secondpartial.home.repository.HomeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: HomeRepository) : ViewModel() {
    private val _ui = MutableStateFlow(HomeUiState())
    val ui: StateFlow<HomeUiState> = _ui

    private val _toastEvents = Channel<String>(Channel.BUFFERED)
    val toastEvents = _toastEvents.receiveAsFlow()

    sealed interface HomeNavEvent

    private val _navEvents = Channel<HomeNavEvent>(Channel.BUFFERED)
    val navEvents = _navEvents.receiveAsFlow()

    fun fetchHome() {
        _ui.value = _ui.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            val result = repo.getRoutines()
            result.onSuccess { response ->
                _ui.value = _ui.value.copy(
                    rutinas = response.rutinas,
                    ejercicios = response.ejercicios,
                    isLoading = false,
                    error = null
                )
            }.onFailure { e ->
                _ui.value = _ui.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
                _toastEvents.send(e.message ?: "Error al cargar Home")
            }
        }
    }
}
