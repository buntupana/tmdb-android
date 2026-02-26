package com.buntupana.tmdb.core.data.database.entity

import androidx.room.Entity
import com.panabuntu.tmdb.core.common.entity.MediaType

@Entity(
    tableName = "favorite",
    primaryKeys = ["mediaId", "mediaType"]
)
data class FavoriteEntity(
    val mediaId: Long,
    val mediaType: MediaType,
    val addedAt: Long = System.currentTimeMillis()
)