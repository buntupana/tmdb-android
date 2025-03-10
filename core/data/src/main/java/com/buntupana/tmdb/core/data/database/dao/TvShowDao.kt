package com.buntupana.tmdb.core.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.TvShowEntity
import com.buntupana.tmdb.core.data.database.entity.TvShowSimpleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Upsert
    suspend fun upsertTvShowDetails(tvShowEntity: TvShowEntity)

    @Upsert(entity = TvShowEntity::class)
    suspend fun upsert(movieItemEntityList: List<TvShowSimpleEntity>)

    @Query("SELECT * FROM tv_show WHERE id = :id")
    fun getById(id: Long): Flow<TvShowEntity>

    @Query("DELETE FROM tv_show WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("""
        SELECT tv_show.* FROM tv_show 
        INNER JOIN watchlist ON tv_show.id = watchlist.mediaId 
        WHERE watchlist.mediaType = 'TV_SHOW'
        ORDER BY watchlist.addedAt ASC
    """)
    fun getWatchlist(): PagingSource<Int, TvShowSimpleEntity>

    @Query("""
        SELECT tv_show.* FROM tv_show 
        INNER JOIN favorite ON tv_show.id = favorite.mediaId 
        WHERE favorite.mediaType = 'TV_SHOW'
        ORDER BY favorite.addedAt ASC
    """)
    fun getFavorites(): PagingSource<Int, TvShowSimpleEntity>

    @Query("UPDATE tv_show SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE tv_show SET isWatchListed = :isWatchListed WHERE id = :id")
    suspend fun updateWatchList(id: Long, isWatchListed: Boolean)

    @Query("UPDATE tv_show SET userRating = :rating WHERE id = :id")
    suspend fun updateRating(id: Long, rating: Int?)

    @Transaction
    suspend fun updateRatingAndWatchlist(id: Long, rating: Int?) {
        updateRating(id = id, rating = rating)
        if (rating != null && rating != 0) {
            updateWatchList(id = id, isWatchListed = false)
        }
    }

    @Query("DELETE FROM tv_show")
    suspend fun clearAll()
}