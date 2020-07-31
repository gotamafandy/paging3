package com.adrena.commerce.paging3.view.viewmodel.rx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adrena.commerce.paging3.data.rx.GetMoviesRxRepository

class GetMoviesRxViewModelFactory(private val repository: GetMoviesRxRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetMoviesRxViewModel::class.java)) {
            return GetMoviesRxViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}