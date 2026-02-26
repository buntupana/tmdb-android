package com.buntupana.tmdb.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKeyEntity(
    @PrimaryKey
    val type: String,
    val nextKey: Int?
)