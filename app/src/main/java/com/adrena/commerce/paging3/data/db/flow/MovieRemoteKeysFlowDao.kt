package com.adrena.commerce.paging3.data.db.flow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adrena.commerce.paging3.data.model.Movies

@Dao
interface MovieRemoteKeysFlowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<Movies.MovieRemoteKeys>)

    @Query("SELECT * FROM movie_remote_keys WHERE movieId = :movieId")
    suspend fun remoteKeysByMovieId(movieId: Long): Movies.MovieRemoteKeys?

    @Query("DELETE FROM movie_remote_keys")
    suspend fun clearRemoteKeys()

}