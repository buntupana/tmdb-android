package com.buntupana.tmdb.feature.seer.domain.manager

import com.buntupana.tmdb.feature.seer.domain.model.SeerSession
import kotlinx.coroutines.flow.StateFlow

interface SeerSessionManager {

    val session: StateFlow<SeerSession>

    suspend fun saveServerUrl(serverUrl: String)

    suspend fun saveSession(session: SeerSession)

    suspend fun clearSession()
}
