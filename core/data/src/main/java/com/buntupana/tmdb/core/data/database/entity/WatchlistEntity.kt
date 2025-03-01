package com.buntupana.tmdb.core.data.database.entity

import androidx.room.Entity
import com.panabuntu.tmdb.core.common.entity.MediaType

@Entity(
    tableName = "watchlist",
    primaryKeys = ["mediaId", "mediaType"]
)
data class WatchlistEntity(
    val mediaId: Long,
    val mediaType: MediaType,
    val addedAt: Long = System.currentTimeMillis()
)