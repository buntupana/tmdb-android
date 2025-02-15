package com.buntupana.tmdb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.TvShowDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDetailsDao {

    @Upsert
    suspend fun upsertTvShowDetails(tvShowDetailsEntity: TvShowDetailsEntity)

    @Query("SELECT * FROM tvshowdetailsentity WHERE id = :id")
    fun getTvShowDetails(id: Long): Flow<TvShowDetailsEntity>

    @Query("DELETE FROM tvshowdetailsentity WHERE id = :id")
    suspend fun deleteTvShowDetails(id: Long)

    @Delete
    suspend fun deleteTvShowDetails(tvShowDetailsEntity: TvShowDetailsEntity)

    @Query("UPDATE tvshowdetailsentity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE tvshowdetailsentity SET isWatchListed = :isWatchListed WHERE id = :id")
    suspend fun updateWatchList(id: Long, isWatchListed: Boolean)

    @Query("UPDATE tvshowdetailsentity SET userRating = :rating WHERE id = :id")
    suspend fun updateRating(id: Long, rating: Int?)

    @Transaction
    open suspend fun updateRatingAndWatchlist(id: Long, rating: Int?) {
        updateRating(id = id, rating = rating)
        if (rating != null && rating != 0) {
            updateWatchList(id = id, isWatchListed = false)
        }
    }

    @Query("DELETE FROM tvshowdetailsentity")
    suspend fun clearAll()

}