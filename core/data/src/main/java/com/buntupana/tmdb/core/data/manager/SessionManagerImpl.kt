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

    private val SESSION_ID_KEY = stringPreferencesKey(name = "SESSION_ID_KEY")
    private val ACCOUNT_ID_KEY = longPreferencesKey(name = "ACCOUNT_ID_KEY")
    private val USERNAME_KEY = stringPreferencesKey(name = "USERNAME_KEY")
    private val NAME_KEY = stringPreferencesKey(name = "NAME_KEY")
    private val AVATAR_URL_KEY = stringPreferencesKey(name = "AVATAR_URL_KEY")

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

    override val session: StateFlow<Session> = runBlocking {
       context.dataStore.data.map {
           val sessionId =
               it[SESSION_ID_KEY] ?: return@map Session(sessionId = null, accountDetails = null)

           val accountId = it[ACCOUNT_ID_KEY]
           val username = it[USERNAME_KEY]
           val name = it[NAME_KEY]
           val avatarUrl = it[AVATAR_URL_KEY]

           Session(
               sessionId = sessionId,
               accountDetails = AccountDetails(
                   id = accountId ?: 0L,
                   username = username.orEmpty(),
                   name = name.orEmpty(),
                   avatarUrl = avatarUrl
               )
           )
       }.stateIn(CoroutineScope(Dispatchers.IO))
   }

    override suspend fun saveSession(session: Session) {
        Timber.d("saveSession() called with: session = [$session]")
        context.dataStore.edit {
            it[SESSION_ID_KEY] = session.sessionId.orEmpty()
            it[ACCOUNT_ID_KEY] = session.accountDetails?.id ?: 0L
            it[USERNAME_KEY] = session.accountDetails?.username.orEmpty()
            it[NAME_KEY] = session.accountDetails?.name.orEmpty()
            it[AVATAR_URL_KEY] = session.accountDetails?.avatarUrl.orEmpty()
        }
    }

    override suspend fun clearSession() {
        Timber.d("clearSession() called")
        context.dataStore.edit { it.clear() }
    }
}