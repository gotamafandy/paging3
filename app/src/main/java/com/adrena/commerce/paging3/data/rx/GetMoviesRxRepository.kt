package com.adrena.commerce.paging3.data.rx

import androidx.paging.Pager
import com.adrena.commerce.paging3.data.model.Movies

interface GetMoviesRxRepository {
    fun getMovies(): Pager<Int, Movies.Movie>
}