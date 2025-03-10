package com.buntupana.tmdb.core.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.MediaListEntity
import com.buntupana.tmdb.core.data.database.entity.MediaListSimpleEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MediaListDao {

    @Upsert
    abstract suspend fun upsert(mediaListEntity: MediaListEntity)

    @Upsert(entity = MediaListEntity::class)
    abstract suspend fun upsert(mediaListSimpleEntity: MediaListSimpleEntity)

    @Upsert
    abstract suspend fun upsert(listEntityMediaList: List<MediaListEntity>)

    @Query("SELECT * FROM media_list WHERE id = :id")
    abstract fun getById(id: Long): Flow<MediaListEntity?>

    @Query("""
        SELECT * FROM media_list
        ORDER BY updatedAt DESC
    """)
    abstract fun getAll(): PagingSource<Int, MediaListEntity>

    @Query("DELETE FROM media_list WHERE id = :id")
    abstract suspend fun deleteById(id: Long)

    @Query("DELETE FROM media_list")
    abstract suspend fun clearAll()
}