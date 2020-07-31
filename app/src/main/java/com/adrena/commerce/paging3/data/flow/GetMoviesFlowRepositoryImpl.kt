package com.adrena.commerce.paging3.data.flow

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.adrena.commerce.paging3.data.model.Movies
import kotlinx.coroutines.flow.Flow

class GetMoviesFlowRepositoryImpl(private val pagingSource: GetMoviesFlowPagingSource): GetMoviesFlowRepository {

    override fun getMovies(): Flow<PagingData<Movies.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 2,
                initialLoadSize = 20),
            pagingSourceFactory = { pagingSource }
        ).flow
    }
}