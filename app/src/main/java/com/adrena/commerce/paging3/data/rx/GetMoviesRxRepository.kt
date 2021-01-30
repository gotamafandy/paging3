package com.adrena.commerce.paging3.data.rx

import androidx.paging.PagingData
import com.adrena.commerce.paging3.data.model.Movies
import io.reactivex.Flowable

interface GetMoviesRxRepository {
    fun getMovies(): Flowable<PagingData<Movies.Movie>>
}