package com.adrena.commerce.paging3.view.viewmodel.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adrena.commerce.paging3.data.flow.GetMoviesFlowRepository

class GetMoviesFlowViewModelFactory(private val repository: GetMoviesFlowRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetMoviesFlowViewModel::class.java)) {
            return GetMoviesFlowViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}