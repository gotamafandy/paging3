package com.adrena.commerce.paging3.data.db.rx

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrena.commerce.paging3.data.model.Movies

@Dao
interface MovieRemoteKeysRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<Movies.MovieRemoteKeys>)

    @Query("SELECT * FROM movie_remote_keys WHERE movieId = :movieId")
    fun remoteKeysByMovieId(movieId: Long): Movies.MovieRemoteKeys?

    @Query("DELETE FROM movie_remote_keys")
    fun clearRemoteKeys()

}