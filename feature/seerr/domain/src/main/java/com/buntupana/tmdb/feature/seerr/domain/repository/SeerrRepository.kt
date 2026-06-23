package com.buntupana.tmdb.feature.seerr.domain.repository

import com.buntupana.tmdb.feature.seerr.domain.model.SeerrMediaInfo
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrUser
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result

interface SeerrRepository {

    suspend fun signInLocal(
        serverUrl: String,
        email: String,
        password: String
    ): Result<SeerrUser, NetworkError>

    suspend fun signInJellyfin(
        serverUrl: String,
        username: String,
        password: String
    ): Result<SeerrUser, NetworkError>

    suspend fun signOut(): Result<Unit, NetworkError>

    suspend fun getMediaInfo(
        mediaType: MediaType,
        mediaId: Long
    ): Result<SeerrMediaInfo, NetworkError>

    suspend fun requestMedia(
        mediaType: MediaType,
        tmdbId: Long,
        seasons: List<Int>?
    ): Result<Unit, NetworkError>
}
