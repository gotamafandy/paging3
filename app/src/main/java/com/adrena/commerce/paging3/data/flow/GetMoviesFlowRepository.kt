package com.adrena.commerce.paging3.data.flow

import androidx.paging.PagingData
import com.adrena.commerce.paging3.data.model.Movies
import kotlinx.coroutines.flow.Flow

interface GetMoviesFlowRepository {
    fun getMovies(): Flow<PagingData<Movies.Movie>>
}