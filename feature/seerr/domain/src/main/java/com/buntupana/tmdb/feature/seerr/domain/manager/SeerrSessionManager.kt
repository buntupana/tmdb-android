package com.buntupana.tmdb.feature.seerr.domain.manager

import com.buntupana.tmdb.feature.seerr.domain.model.SeerrSession
import kotlinx.coroutines.flow.StateFlow

interface SeerrSessionManager {

    val session: StateFlow<SeerrSession>

    suspend fun saveServerUrl(serverUrl: String)

    suspend fun saveSession(session: SeerrSession)

    suspend fun clearSession()
}
