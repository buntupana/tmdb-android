package com.buntupana.tmdb.feature.seerr.data.repository

import com.buntupana.tmdb.feature.seerr.data.mapper.toDomain
import com.buntupana.tmdb.feature.seerr.data.remote_data_source.SeerrRemoteDataSource
import com.buntupana.tmdb.feature.seerr.domain.manager.SeerrSessionManager
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrMediaInfo
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrSession
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrUser
import com.buntupana.tmdb.feature.seerr.domain.repository.SeerrRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map

class SeerrRepositoryImpl(
    private val remoteDataSource: SeerrRemoteDataSource,
    private val sessionManager: SeerrSessionManager
) : SeerrRepository {

    override suspend fun signInLocal(
        serverUrl: String,
        email: String,
        password: String
    ): Result<SeerrUser, NetworkError> {
        val result = remoteDataSource.signInLocal(serverUrl, email, password)
        if (result is Result.Success) {
            val user = result.data.user.toDomain()
            sessionManager.saveSession(
                SeerrSession(
                    serverUrl = serverUrl,
                    cookie = result.data.cookie,
                    user = user
                )
            )
        }
        return result.map { it.user.toDomain() }
    }

    override suspend fun signInJellyfin(
        serverUrl: String,
        username: String,
        password: String
    ): Result<SeerrUser, NetworkError> {
        val result = remoteDataSource.signInJellyfin(serverUrl, username, password)
        if (result is Result.Success) {
            val user = result.data.user.toDomain()
            sessionManager.saveSession(
                SeerrSession(
                    serverUrl = serverUrl,
                    cookie = result.data.cookie,
                    user = user
                )
            )
        }
        return result.map { it.user.toDomain() }
    }

    override suspend fun signOut(): Result<Unit, NetworkError> {
        val session = sessionManager.session.value
        val serverUrl = session.serverUrl
        val cookie = session.cookie
        if (!serverUrl.isNullOrBlank() && !cookie.isNullOrBlank()) {
            remoteDataSource.signOut(serverUrl, cookie)
        }
        sessionManager.clearSession()
        return Result.Success(Unit)
    }

    override suspend fun getMediaInfo(
        mediaType: MediaType,
        mediaId: Long
    ): Result<SeerrMediaInfo, NetworkError> {
        val session = sessionManager.session.value
        val serverUrl = session.serverUrl
        val cookie = session.cookie
        if (serverUrl.isNullOrBlank() || cookie.isNullOrBlank()) {
            return Result.Error(NetworkError.UNAUTHORIZED)
        }
        return remoteDataSource.getMedia(serverUrl, cookie, mediaType, mediaId).map { it.toDomain() }
    }

    override suspend fun requestMedia(
        mediaType: MediaType,
        tmdbId: Long,
        seasons: List<Int>?
    ): Result<Unit, NetworkError> {
        val session = sessionManager.session.value
        val serverUrl = session.serverUrl
        val cookie = session.cookie
        if (serverUrl.isNullOrBlank() || cookie.isNullOrBlank()) {
            return Result.Error(NetworkError.UNAUTHORIZED)
        }
        return remoteDataSource.createRequest(serverUrl, cookie, mediaType, tmdbId, seasons)
    }
}
