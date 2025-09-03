package com.jorgeromo.androidClassMp1.firstpartial.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.model.OnboardingContent
import com.jorgeromo.androidClassMp1.firstpartial.onboarding.model.OnboardingPageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "OnboardingViewModel"

class OnboardingViewModel : ViewModel() {
    private val _pages = MutableStateFlow(OnboardingContent.pages)
    val pages: StateFlow<List<OnboardingPageModel>> = _pages.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val lastIndex: Int get() = _pages.value.lastIndex

    init {
        Log.i(TAG, "ViewModel inicializado con ${_pages.value.size} páginas")
    }

    fun setPage(index: Int) {
        val safeIndex = index.coerceIn(0, lastIndex)
        Log.d(TAG, "Cambiando página de ${_currentPage.value} a $safeIndex")
        _currentPage.value = safeIndex
    }

    fun nextPage() {
        Log.d(TAG, "Solicitando página siguiente")
        setPage(_currentPage.value + 1)
    }

    fun prevPage() {
        Log.d(TAG, "Solicitando página anterior")
        setPage(_currentPage.value - 1)
    }

    fun isLastPage(index: Int = _currentPage.value): Boolean {
        val isLast = index == lastIndex
        Log.v(TAG, "Verificando si es última página ($index): $isLast")
        return isLast
    }
}