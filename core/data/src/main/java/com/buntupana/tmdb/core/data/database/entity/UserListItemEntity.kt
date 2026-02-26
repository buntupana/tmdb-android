package com.buntupana.tmdb.core.data.database.entity

import androidx.room.Entity
import com.panabuntu.tmdb.core.common.entity.MediaType

@Entity(
    tableName = "user_list_item",
    primaryKeys = ["listId", "mediaId", "mediaType"]
)
data class UserListItemEntity(
    val listId: Long,
    val mediaId: Long,
    val mediaType: MediaType,
    val addedAt: Long = System.currentTimeMillis()
)