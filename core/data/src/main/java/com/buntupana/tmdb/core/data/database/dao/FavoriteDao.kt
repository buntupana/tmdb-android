package com.buntupana.tmdb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.FavoriteEntity
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
abstract class FavoriteDao {

    @Upsert
    abstract suspend fun upsert(favoriteEntity: FavoriteEntity)

    @Upsert
    abstract suspend fun upsert(favoriteEntityList: List<FavoriteEntity>)

    @Transaction
    open suspend fun insert(mediaId: Long, mediaType: MediaType) {
        val media = getMedia(mediaType).first().firstOrNull()

        val addedAt = if (media == null) {
            System.currentTimeMillis()
        } else {
            media.addedAt - 1
        }

        upsert(
            FavoriteEntity(
                mediaId = mediaId,
                mediaType = mediaType,
                addedAt = addedAt
            )
        )
    }

    @Query("SELECT * FROM favorite WHERE mediaType = :mediaType ORDER BY favorite.addedAt ASC")
    abstract fun getMedia(mediaType: MediaType): Flow<List<FavoriteEntity>>

    @Query("DELETE FROM favorite WHERE mediaId = :mediaId AND mediaType = :mediaType")
    abstract suspend fun delete(mediaId: Long, mediaType: MediaType)

    @Query("DELETE FROM favorite WHERE mediaType = :mediaType")
    abstract suspend fun clearByMediaType(mediaType: MediaType)

    @Query("DELETE FROM favorite")
    abstract suspend fun clearAll()
}