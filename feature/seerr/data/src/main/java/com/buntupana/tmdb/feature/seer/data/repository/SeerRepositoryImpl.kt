package com.buntupana.tmdb.feature.seer.data.repository

import com.buntupana.tmdb.feature.seer.data.mapper.toDomain
import com.buntupana.tmdb.feature.seer.data.remote_data_source.SeerrRemoteDataSource
import com.buntupana.tmdb.feature.seer.domain.manager.SeerSessionManager
import com.buntupana.tmdb.feature.seer.domain.model.SeerMediaInfo
import com.buntupana.tmdb.feature.seer.domain.model.SeerSession
import com.buntupana.tmdb.feature.seer.domain.model.SeerUser
import com.buntupana.tmdb.feature.seer.domain.repository.SeerRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map

class SeerRepositoryImpl(
    private val remoteDataSource: SeerrRemoteDataSource,
    private val sessionManager: SeerSessionManager
) : SeerRepository {

    override suspend fun signInLocal(
        serverUrl: String,
        email: String,
        password: String
    ): Result<SeerUser, NetworkError> {
        val result = remoteDataSource.signInLocal(serverUrl, email, password)
        if (result is Result.Success) {
            val user = result.data.user.toDomain()
            sessionManager.saveSession(
                SeerSession(
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
    ): Result<SeerUser, NetworkError> {
        val result = remoteDataSource.signInJellyfin(serverUrl, username, password)
        if (result is Result.Success) {
            val user = result.data.user.toDomain()
            sessionManager.saveSession(
                SeerSession(
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
    ): Result<SeerMediaInfo, NetworkError> {
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
