package com.buntupana.tmdb.feature.seer.domain.repository

import com.buntupana.tmdb.feature.seer.domain.model.SeerMediaInfo
import com.buntupana.tmdb.feature.seer.domain.model.SeerUser
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

interface SeerRepository {

    suspend fun signInLocal(
        serverUrl: String,
        email: String,
        password: String
    ): Result<SeerUser, NetworkError>

    suspend fun signInJellyfin(
        serverUrl: String,
        username: String,
        password: String
    ): Result<SeerUser, NetworkError>

    suspend fun signOut(): Result<Unit, NetworkError>

    suspend fun getMediaInfo(
        mediaType: MediaType,
        mediaId: Long
    ): Result<SeerMediaInfo, NetworkError>

    suspend fun requestMedia(
        mediaType: MediaType,
        tmdbId: Long,
        seasons: List<Int>?
    ): Result<Unit, NetworkError>
}
