package com.adrena.commerce.paging3.data.rx

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.adrena.commerce.paging3.data.db.MovieDatabase
import com.adrena.commerce.paging3.data.model.Movies

class GetMoviesRxRemoteRepositoryImpl(
    private val database: MovieDatabase,
    private val remoteMediator: GetMoviesRxRemoteMediator
): GetMoviesRxRepository {

    override fun getMovies(): Pager<Int, Movies.Movie> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 2,
                initialLoadSize = 20),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.moviesRxDao().selectAll() }
        )
    }
}