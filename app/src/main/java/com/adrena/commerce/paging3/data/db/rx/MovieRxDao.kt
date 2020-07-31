package com.adrena.commerce.paging3.data.db.rx

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrena.commerce.paging3.data.model.Movies

@Dao
interface MovieRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movies.Movie>)

    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, Movies.Movie>

    @Query("DELETE FROM movies")
    fun clearMovies()

}