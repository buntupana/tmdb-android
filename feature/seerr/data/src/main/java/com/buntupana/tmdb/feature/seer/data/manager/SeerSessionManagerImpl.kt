package com.buntupana.tmdb.feature.seer.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.buntupana.tmdb.feature.seer.domain.manager.SeerSessionManager
import com.buntupana.tmdb.feature.seer.domain.model.SeerSession
import com.buntupana.tmdb.feature.seer.domain.model.SeerUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import timber.log.Timber

private val Context.seerSessionDataStore by preferencesDataStore(name = "seer_session")

class SeerSessionManagerImpl(
    private val context: Context
) : SeerSessionManager {

    companion object {
        private val SERVER_URL_KEY = stringPreferencesKey(name = "SEER_SERVER_URL_KEY")
        private val COOKIE_KEY = stringPreferencesKey(name = "SEER_COOKIE_KEY")
        private val USER_ID_KEY = longPreferencesKey(name = "SEER_USER_ID_KEY")
        private val USER_EMAIL_KEY = stringPreferencesKey(name = "SEER_USER_EMAIL_KEY")
        private val USER_DISPLAY_NAME_KEY = stringPreferencesKey(name = "SEER_USER_DISPLAY_NAME_KEY")
        private val USER_AVATAR_KEY = stringPreferencesKey(name = "SEER_USER_AVATAR_KEY")
    }

    override val session: StateFlow<SeerSession> = runBlocking {

        context.seerSessionDataStore.data.map { prefs ->

            val serverUrl = prefs[SERVER_URL_KEY]
            val cookie = prefs[COOKIE_KEY]
            val userId = prefs[USER_ID_KEY]

            if (serverUrl.isNullOrBlank()) {
                return@map SeerSession.EMPTY
            }

            val user = userId?.let {
                SeerUser(
                    id = userId,
                    email = prefs[USER_EMAIL_KEY],
                    displayName = prefs[USER_DISPLAY_NAME_KEY],
                    avatar = prefs[USER_AVATAR_KEY]
                )
            }

            SeerSession(
                serverUrl = serverUrl,
                cookie = cookie,
                user = user
            )
        }.stateIn(CoroutineScope(Dispatchers.IO))
    }

    override suspend fun saveServerUrl(serverUrl: String) {
        Timber.d("saveServerUrl() called with: serverUrl = [$serverUrl]")
        context.seerSessionDataStore.edit {
            it[SERVER_URL_KEY] = serverUrl.normalizedServerUrl()
        }
    }

    override suspend fun saveSession(session: SeerSession) {
        Timber.d("saveSession() called")
        context.seerSessionDataStore.edit { prefs ->
            session.serverUrl?.let { prefs[SERVER_URL_KEY] = it.normalizedServerUrl() }
            prefs[COOKIE_KEY] = session.cookie.orEmpty()
            session.user?.let { user ->
                prefs[USER_ID_KEY] = user.id
                prefs[USER_EMAIL_KEY] = user.email.orEmpty()
                prefs[USER_DISPLAY_NAME_KEY] = user.displayName.orEmpty()
                prefs[USER_AVATAR_KEY] = user.avatar.orEmpty()
            }
        }
    }

    override suspend fun clearSession() {
        Timber.d("clearSession() called")
        context.seerSessionDataStore.edit { it.clear() }
    }

    private fun String.normalizedServerUrl(): String =
        trim().trimEnd('/')
}
