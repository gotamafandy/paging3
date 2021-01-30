package com.adrena.commerce.paging3.data.flow

import androidx.paging.PagingSource
import androidx.paging.PagingState
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