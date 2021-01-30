package com.adrena.commerce.paging3.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.adrena.commerce.paging3.R
import com.adrena.commerce.paging3.data.db.MovieDatabase
import com.adrena.commerce.paging3.data.flow.GetMoviesFlowPagingSource
import com.adrena.commerce.paging3.data.flow.GetMoviesFlowRemoteMediator
import com.adrena.commerce.paging3.data.flow.GetMoviesFlowRemoteRepositoryImpl
import com.adrena.commerce.paging3.data.flow.GetMoviesFlowRepositoryImpl
import com.adrena.commerce.paging3.data.model.MoviesMapper
import com.adrena.commerce.paging3.data.rx.GetMoviesRxPagingSource
import com.adrena.commerce.paging3.data.rx.GetMoviesRxRemoteMediator
import com.adrena.commerce.paging3.data.rx.GetMoviesRxRemoteRepositoryImpl
import com.adrena.commerce.paging3.data.rx.GetMoviesRxRepositoryImpl
import com.adrena.commerce.paging3.view.viewmodel.flow.GetMoviesFlowViewModelFactory
import com.adrena.commerce.paging3.view.viewmodel.rx.GetMoviesRxViewModelFactory
import java.util.*

object Injection {
    fun provideLocale(): Locale = Locale.getDefault()
    private fun provideDatabase(context: Context): MovieDatabase = MovieDatabase.getInstance(context)

    fun provideFlowViewModel(context: Context): ViewModelProvider.Factory {
        val pagingSource =
            GetMoviesFlowPagingSource(
                service = TMDBService.create(),
                apiKey = context.getString(R.string.api_key),
                mapper = MoviesMapper(),
                locale = provideLocale()
            )

        val repository =
            GetMoviesFlowRepositoryImpl(
                pagingSource = pagingSource
            )

        return GetMoviesFlowViewModelFactory(
            repository
        )
    }

    fun provideFlowRemoteViewModel(context: Context): ViewModelProvider.Factory {
        val remoteMediator =
            GetMoviesFlowRemoteMediator(
                service = TMDBService.create(),
                database = provideDatabase(context),
                apiKey = context.getString(R.string.api_key),
                mapper = MoviesMapper(),
                locale = provideLocale()
            )

        val repository =
            GetMoviesFlowRemoteRepositoryImpl(
                database = provideDatabase(context),
                remoteMediator = remoteMediator
            )

        return GetMoviesFlowViewModelFactory(
            repository
        )
    }

    fun provideRxViewModel(context: Context): ViewModelProvider.Factory {
        val pagingSource =
            GetMoviesRxPagingSource(
                service = TMDBService.create(),
                apiKey = context.getString(R.string.api_key),
                mapper = MoviesMapper(),
                locale = provideLocale()
            )

        val repository =
            GetMoviesRxRepositoryImpl(
                pagingSource = pagingSource
            )

        return GetMoviesRxViewModelFactory(
            repository
        )
    }

    fun provideRxRemoteViewModel(context: Context): ViewModelProvider.Factory {
        val remoteMediator =
            GetMoviesRxRemoteMediator(
                service = TMDBService.create(),
                database = provideDatabase(context),
                apiKey = context.getString(R.string.api_key),
                mapper = MoviesMapper(),
                locale = provideLocale()
            )

        val repository =
            GetMoviesRxRemoteRepositoryImpl(
                database = provideDatabase(context),
                remoteMediator = remoteMediator
            )

        return GetMoviesRxViewModelFactory(
            repository
        )
    }
}