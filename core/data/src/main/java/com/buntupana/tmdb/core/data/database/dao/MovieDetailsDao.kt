package com.buntupana.tmdb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.MovieDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDetailsDao {

    @Upsert
    abstract suspend fun upsertMovieDetails(movieDetailsEntity: MovieDetailsEntity)

    @Query("SELECT * FROM moviedetailsentity WHERE id = :id")
    abstract fun getMovieDetails(id: Long): Flow<MovieDetailsEntity>

    @Query("DELETE FROM moviedetailsentity WHERE id = :id")
    abstract suspend fun deleteMovieDetails(id: Long)

    @Delete
    abstract suspend fun deleteMovieDetails(movieDetailsEntity: MovieDetailsEntity)

    @Query("UPDATE moviedetailsentity SET isFavorite = :isFavorite WHERE id = :id")
    abstract suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE moviedetailsentity SET isWatchListed = :isWatchListed WHERE id = :id")
    abstract suspend fun updateWatchList(id: Long, isWatchListed: Boolean)

    @Query("UPDATE moviedetailsentity SET userRating = :rating WHERE id = :id")
    abstract suspend fun updateRating(id: Long, rating: Int?)

    @Transaction
    open suspend fun updateRatingAndWatchlist(id: Long, rating: Int?) {
        updateRating(id = id, rating = rating)
        if (rating != null && rating != 0) {
            updateWatchList(id = id, isWatchListed = false)
        }
    }

    @Query("DELETE FROM moviedetailsentity")
    abstract suspend fun clearAll()

}