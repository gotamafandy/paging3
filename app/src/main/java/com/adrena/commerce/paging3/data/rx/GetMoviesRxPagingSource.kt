package com.adrena.commerce.paging3.data.rx

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.adrena.commerce.paging3.data.TMDBService
import com.adrena.commerce.paging3.data.model.Movies
import com.adrena.commerce.paging3.data.model.MoviesMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

class GetMoviesRxPagingSource(
    private val service: TMDBService,
    private val apiKey: String,
    private val mapper: MoviesMapper,
    private val locale: Locale
) : RxPagingSource<Int, Movies.Movie>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movies.Movie>> {
        val position = params.key ?: 1

        return service.popularMovieRx(apiKey, position, locale.language)
            .subscribeOn(Schedulers.io())
            .map { mapper.transform(it, locale) }
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: Movies, position: Int): LoadResult<Int, Movies.Movie> {
        return LoadResult.Page(
            data = data.movies,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.total) null else position + 1
        )
    }

    /**
     * Provide a [Key] used for the initial [load] for the next [PagingSource] due to invalidation
     * of this [PagingSource]. The [Key] is provided to [load] via [LoadParams.key].
     *
     * The [Key] returned by this method should cause [load] to load enough items to
     * fill the viewport around the last accessed position, allowing the next generation to
     * transparently animate in. The last accessed position can be retrieved via
     * [state.anchorPosition][PagingState.anchorPosition], which is typically
     * the top-most or bottom-most item in the viewport due to access being triggered by binding
     * items as they scroll into view.
     *
     * For example, if items are loaded based on integer position keys, you can return
     * [state.anchorPosition][PagingState.anchorPosition].
     *
     * Alternately, if items contain a key used to load, get the key from the item in the page at
     * index [state.anchorPosition][PagingState.anchorPosition].
     *
     * @param state [PagingState] of the currently fetched data, which includes the most recently
     * accessed position in the list via [PagingState.anchorPosition].
     *
     * @return [Key] passed to [load] after invalidation used for initial load of the next
     * generation. The [Key] returned by [getRefreshKey] should load pages centered around
     * user's current viewport. If the correct [Key] cannot be determined, `null` can be returned
     * to allow [load] decide what default key to use.
     */
    override fun getRefreshKey(state: PagingState<Int, Movies.Movie>): Int? {
        return state.anchorPosition
    }
}