package com.adrena.commerce.paging3.data.rx

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.adrena.commerce.paging3.data.model.Movies

class GetMoviesRxRepositoryImpl(private val pagingSource: GetMoviesRxPagingSource): GetMoviesRxRepository {

    override fun getMovies(): Pager<Int, Movies.Movie> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 2,
                initialLoadSize = 20),
            pagingSourceFactory = { pagingSource }
        )
    }
}