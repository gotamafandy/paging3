package com.adrena.commerce.paging3.data.flow

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.adrena.commerce.paging3.data.TMDBService
import com.adrena.commerce.paging3.data.db.MovieDatabase
import com.adrena.commerce.paging3.data.model.Movies
import com.adrena.commerce.paging3.data.model.MoviesMapper
import java.io.InvalidObjectException
import java.util.*

@OptIn(ExperimentalPagingApi::class)
class GetMoviesFlowRemoteMediator(
    private val service: TMDBService,
    private val database: MovieDatabase,
    private val apiKey: String,
    private val mapper: MoviesMapper,
    private val locale: Locale
) : RemoteMediator<Int, Movies.Movie>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movies.Movie>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val nextKey = remoteKeys.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                nextKey
            }
        }

        try {
            val response = service.moviesFlow(
                apiKey = apiKey,
                page = page,
                language = locale.language)

            val data = mapper.transform(response, locale)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieRemoteKeysFlowDao().clearRemoteKeys()
                    database.moviesFlowDao().clearMovies()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (data.endOfPage) null else page + 1
                val keys = data.movies.map {
                    Movies.MovieRemoteKeys(movieId = it.movieId, prevKey = prevKey, nextKey = nextKey)
                }
                database.movieRemoteKeysFlowDao().insertAll(keys)
                database.moviesFlowDao().insertAll(data.movies)
            }

            return MediatorResult.Success(endOfPaginationReached = data.endOfPage)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.movieRemoteKeysFlowDao().remoteKeysByMovieId(repo.movieId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.movieRemoteKeysFlowDao().remoteKeysByMovieId(movie.movieId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movies.Movie>): Movies.MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                database.movieRemoteKeysFlowDao().remoteKeysByMovieId(id)
            }
        }
    }
}
