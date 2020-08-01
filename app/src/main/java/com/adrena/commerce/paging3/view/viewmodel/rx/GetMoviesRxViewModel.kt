package com.adrena.commerce.paging3.view.viewmodel.rx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.adrena.commerce.paging3.data.model.Movies
import com.adrena.commerce.paging3.data.rx.GetMoviesRxRepository
import io.reactivex.Flowable

class GetMoviesRxViewModel(private val repository: GetMoviesRxRepository) : ViewModel() {
    fun getFavoriteMovies(): Flowable<PagingData<Movies.Movie>> {
        return repository
            .getMovies()
            .map { pagingData -> pagingData.filter { it.poster != null } }
            .cachedIn(viewModelScope)
    }
}