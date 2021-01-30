package com.adrena.commerce.paging3.data.flow

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.adrena.commerce.paging3.data.db.MovieDatabase
import com.adrena.commerce.paging3.data.model.Movies
import kotlinx.coroutines.flow.Flow

class GetMoviesFlowRemoteRepositoryImpl(
    private val database: MovieDatabase,
    private val remoteMediator: GetMoviesFlowRemoteMediator): GetMoviesFlowRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<Movies.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5,
                initialLoadSize = 40),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.moviesFlowDao().selectAll() }
        ).flow
    }
}