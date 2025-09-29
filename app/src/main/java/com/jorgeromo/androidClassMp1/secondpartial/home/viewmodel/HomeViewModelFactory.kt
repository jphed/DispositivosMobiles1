package com.jorgeromo.androidClassMp1.secondpartial.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jorgeromo.androidClassMp1.secondpartial.home.repository.HomeRepository

class HomeViewModelFactory(
    private val repo: HomeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}
