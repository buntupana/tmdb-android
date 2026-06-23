package com.buntupana.tmdb.feature.seer.presentation.status

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.seer.domain.manager.SeerSessionManager
import com.buntupana.tmdb.feature.seer.domain.usecase.SignOutSeerUseCase
import com.panabuntu.tmdb.core.common.provider.SeerStatusProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class SeerStatusState(
    val isConnected: Boolean = false,
    val serverUrl: String? = null,
    val userDisplayName: String? = null
)

class SeerStatusViewModel(
    private val sessionManager: SeerSessionManager,
    private val signOutUseCase: SignOutSeerUseCase,
    private val statusProvider: SeerStatusProvider
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
