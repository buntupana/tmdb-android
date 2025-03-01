package com.buntupana.tmdb.core.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.AnyMediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AnyMediaDao {

    @Upsert
    abstract suspend fun upsertAnyMedia(anyMediaEntityList: List<AnyMediaEntity>)

    @Query("SELECT * FROM any_media WHERE id = :mediaId AND mediaType = :mediaType")
    abstract fun getAnyMedia(mediaId: Long, mediaType: String): Flow<AnyMediaEntity>

    @Query("""
        SELECT * FROM any_media 
        WHERE (id, mediaType) IN (SELECT mediaId, mediaType FROM watchlist) 
        ORDER BY id ASC
    """)
    abstract fun getWatchlistMovies(): PagingSource<Int, AnyMediaEntity>

    @Query("DELETE FROM any_media WHERE id = :mediaId AND mediaType = :mediaType")
    abstract suspend fun deleteSeasonEpisodes(mediaId: Long, mediaType: String)

    @Delete
    abstract suspend fun deleteAnyMedia(episodesEntityList: List<AnyMediaEntity>)

    @Query("DELETE FROM any_media")
    abstract suspend fun clearAll()
}