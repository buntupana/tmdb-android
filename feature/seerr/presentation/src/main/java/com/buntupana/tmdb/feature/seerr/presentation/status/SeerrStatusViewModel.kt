package com.buntupana.tmdb.feature.seerr.presentation.status

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.seerr.domain.manager.SeerrSessionManager
import com.buntupana.tmdb.feature.seerr.domain.usecase.SignOutSeerrUseCase
import com.panabuntu.tmdb.core.common.provider.SeerrStatusProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class SeerStatusState(
    val isConnected: Boolean = false,
    val serverUrl: String? = null,
    val userDisplayName: String? = null
)

class SeerStatusViewModel(
    private val sessionManager: SeerrSessionManager,
    private val signOutUseCase: SignOutSeerrUseCase,
    private val statusProvider: SeerrStatusProvider
) : ViewModel() {

    var state by mutableStateOf(SeerStatusState())
        private set

    init {
        viewModelScope.launch {
            sessionManager.session.collectLatest { session ->
                state = SeerStatusState(
                    isConnected = session.isLogged,
                    serverUrl = session.serverUrl,
                    userDisplayName = session.user?.displayName ?: session.user?.email
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            statusProvider.invalidateAll()
        }
    }
}
