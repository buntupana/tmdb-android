package com.buntupana.tmdb.core.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.model.AccountDetails
import com.panabuntu.tmdb.core.common.model.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class SessionManagerImpl(
    private val context: Context
) : SessionManager {

    private val ACCESS_TOKEN_KEY = stringPreferencesKey(name = "ACCESS_TOKEN_KEY")
    private val SESSION_ID_KEY = stringPreferencesKey(name = "SESSION_ID_KEY")
    private val ACCOUNT_ID_KEY = longPreferencesKey(name = "ACCOUNT_ID_KEY")
    private val ACCOUNT_OBJECT_ID_KEY = stringPreferencesKey(name = "ACCOUNT_OBJECT_ID_KEY")
    private val USERNAME_KEY = stringPreferencesKey(name = "USERNAME_KEY")
    private val NAME_KEY = stringPreferencesKey(name = "NAME_KEY")
    private val AVATAR_URL_KEY = stringPreferencesKey(name = "AVATAR_URL_KEY")

    private val REQUEST_TOKEN_KEY = stringPreferencesKey(name = "REQUEST_TOKEN_KEY")

    private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    override val session: StateFlow<Session> = runBlocking {
        context.sessionDataStore.data.map {

            val accessToken = it[ACCESS_TOKEN_KEY]

            val sessionId = it[SESSION_ID_KEY]

            if (accessToken == null || sessionId == null) {
                return@map Session(accessToken = null, sessionId = null, accountDetails = null)
            }

            val accountId = it[ACCOUNT_ID_KEY]
            val accountObjectId = it[ACCOUNT_OBJECT_ID_KEY]
            val username = it[USERNAME_KEY]
            val name = it[NAME_KEY]
            val avatarUrl = it[AVATAR_URL_KEY]

            Session(
                accessToken = accessToken,
                sessionId = sessionId,
                accountDetails = AccountDetails(
                    id = accountId ?: 0L,
                    accountObjectId = accountObjectId.orEmpty(),
                    username = username.orEmpty(),
                    name = name.orEmpty(),
                    avatarUrl = avatarUrl
                )
            )
        }.stateIn(CoroutineScope(Dispatchers.IO))
    }

    override suspend fun saveSession(session: Session) {
        Timber.d("saveSession() called with: session = [$session]")
        context.sessionDataStore.edit {
            it[ACCESS_TOKEN_KEY] = session.accessToken.orEmpty()
            it[SESSION_ID_KEY] = session.sessionId.orEmpty()
            it[ACCOUNT_ID_KEY] = session.accountDetails?.id ?: 0L
            it[ACCOUNT_OBJECT_ID_KEY] = session.accountDetails?.accountObjectId.orEmpty()
            it[USERNAME_KEY] = session.accountDetails?.username.orEmpty()
            it[NAME_KEY] = session.accountDetails?.name.orEmpty()
            it[AVATAR_URL_KEY] = session.accountDetails?.avatarUrl.orEmpty()
        }
    }

    override suspend fun clearSession() {
        Timber.d("clearSession() called")
        context.sessionDataStore.edit { it.clear() }
    }

    override suspend fun getRequestToken(): String? {
        return context.sessionDataStore.data.map {
            it[REQUEST_TOKEN_KEY]
        }.stateIn(CoroutineScope(Dispatchers.IO)).value
    }

    override suspend fun saveRequestToken(requestToken: String) {
        Timber.d("saveRequestToken() called with: requestToken = [$requestToken]")
        context.sessionDataStore.edit {
            it[REQUEST_TOKEN_KEY] = requestToken
        }
    }

    override suspend fun removeRequestToken() {
        context.sessionDataStore.edit {
            if (it.contains(REQUEST_TOKEN_KEY)) {
                it.remove(REQUEST_TOKEN_KEY)
            }
        }
    }
}