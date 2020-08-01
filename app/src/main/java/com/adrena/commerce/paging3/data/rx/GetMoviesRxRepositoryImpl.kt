package com.adrena.commerce.paging3.data.rx

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.adrena.commerce.paging3.data.model.Movies
import io.reactivex.Flowable

class GetMoviesRxRepositoryImpl(private val pagingSource: GetMoviesRxPagingSource): GetMoviesRxRepository {

    override fun getMovies(): Flowable<PagingData<Movies.Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }
}