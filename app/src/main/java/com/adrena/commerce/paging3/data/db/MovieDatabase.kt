package com.adrena.commerce.paging3.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adrena.commerce.paging3.data.db.flow.MovieFlowDao
import com.adrena.commerce.paging3.data.db.flow.MovieRemoteKeysFlowDao
import com.adrena.commerce.paging3.data.db.rx.MovieRemoteKeysRxDao
import com.adrena.commerce.paging3.data.db.rx.MovieRxDao
import com.adrena.commerce.paging3.data.model.Movies

@Database(
    entities = [Movies.Movie::class, Movies.MovieRemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun moviesFlowDao(): MovieFlowDao
    abstract fun movieRemoteKeysFlowDao(): MovieRemoteKeysFlowDao

    abstract fun moviesRxDao(): MovieRxDao
    abstract fun movieRemoteKeysRxDao(): MovieRemoteKeysRxDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MovieDatabase::class.java, "TMDB.db")
                .build()
    }
}