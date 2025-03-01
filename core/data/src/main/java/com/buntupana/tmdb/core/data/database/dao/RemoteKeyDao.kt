package com.buntupana.tmdb.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.buntupana.tmdb.core.data.database.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Upsert
    suspend fun upsert(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_key WHERE type LIKE :type")
    suspend fun remoteKeyByType(type: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_key WHERE type LIKE :type")
    suspend fun clearType(type: String)

    @Query("DELETE FROM remote_key")
    suspend fun clearAll()
}