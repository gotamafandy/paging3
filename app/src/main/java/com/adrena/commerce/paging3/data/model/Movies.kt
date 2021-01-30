package com.adrena.commerce.paging3.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Movies(
    val total: Int = 0,
    val page: Int = 0,
    val movies: List<Movie>
) : Parcelable {

    @IgnoredOnParcel
    val endOfPage = total == page

    @Parcelize
    @Entity(tableName = "movies")
    data class Movie(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val movieId: Long,
        val popularity: Double,
        val video: Boolean,
        val poster: Image?,
        val adult: Boolean,
        val backdrop: Image?,
        val originalLanguage: String,
        val originalTitle: String,
        val title: String,
        val voteAverage: Double,
        val overview: String,
        val releaseDate: Date?
    ) : Parcelable

    @Parcelize
    @Entity(tableName = "movie_remote_keys")
    data class MovieRemoteKeys(
        @PrimaryKey val movieId: Long,
        val prevKey: Int?,
        val nextKey: Int?
    ) : Parcelable
}
