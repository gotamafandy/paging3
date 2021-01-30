package com.adrena.commerce.paging3.data.rx

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.adrena.commerce.paging3.data.db.MovieDatabase
import com.adrena.commerce.paging3.data.model.Movies
import io.reactivex.Flowable

class GetMoviesRxRemoteRepositoryImpl(
    private val database: MovieDatabase,
    private val remoteMediator: GetMoviesRxRemoteMediator
): GetMoviesRxRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flowable<PagingData<Movies.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.moviesRxDao().selectAll() }
        ).flowable
    }
}