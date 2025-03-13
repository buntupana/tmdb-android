package com.buntupana.tmdb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.UserListItemEntity
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
abstract class UserListItemDao {

    @Upsert
    abstract suspend fun upsert(userListItemEntity: UserListItemEntity)

    @Upsert
    abstract suspend fun upsert(userListItemEntityList: List<UserListItemEntity>)

    @Transaction
    open suspend fun insert(listId: Long, mediaId: Long, mediaType: MediaType) {
        val media = getByListId(listId).first().lastOrNull()

        val addedAt = if (media == null) {
            System.currentTimeMillis()
        } else {
            media.addedAt + 1
        }

        upsert(
            UserListItemEntity(
                listId = listId,
                mediaId = mediaId,
                mediaType = mediaType,
                addedAt = addedAt
            )
        )
    }

    @Query("""
       SELECT * FROM user_list_item 
        WHERE listId = :listId 
        ORDER BY user_list_item.addedAt ASC
    """)
    abstract fun getByListId(listId: Long): Flow<List<UserListItemEntity?>>

    @Query("DELETE FROM user_list_item WHERE listId = :listId AND mediaId = :mediaId AND mediaType = :mediaType")
    abstract suspend fun delete(listId: Long, mediaId: Long, mediaType: MediaType)

    @Query("DELETE FROM user_list_item WHERE listId = :listId")
    abstract suspend fun clearByListId(listId: Long)

    @Query("DELETE FROM watchlist")
    abstract suspend fun clearAll()
}