package com.adrena.commerce.paging3.data.flow

import androidx.paging.PagingSource
import com.adrena.commerce.paging3.data.TMDBService
import com.adrena.commerce.paging3.data.model.Movies
import com.adrena.commerce.paging3.data.model.MoviesMapper
import java.util.*

class GetMoviesFlowPagingSource(
    private val service: TMDBService,
    private val apiKey: String,
    private val mapper: MoviesMapper,
    private val locale: Locale
) : PagingSource<Int, Movies.Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies.Movie> {
        val position = params.key ?: 1

        return try {
            service.moviesFlow(
                apiKey = apiKey,
                language = locale.language,
                page = position
            ).run {
                val data = mapper.transform(this, locale)

                LoadResult.Page(
                    data = data.movies,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (position == this.total) null else position + 1
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}