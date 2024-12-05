package com.panabuntu.tmdb.core.common.manager

import com.panabuntu.tmdb.core.common.model.Session
import kotlinx.coroutines.flow.StateFlow

interface SessionManager {

    val session: StateFlow<Session>

    suspend fun saveSession(session: Session)

    suspend fun clearSession()
}