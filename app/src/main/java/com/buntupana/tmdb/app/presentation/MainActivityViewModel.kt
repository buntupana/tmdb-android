package com.buntupana.tmdb.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.account.domain.usecase.ReloadAccountDetailsUseCase
import com.panabuntu.tmdb.core.common.manager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    sessionManager: SessionManager,
    private val reloadAccountDetailsUseCase: ReloadAccountDetailsUseCase
): ViewModel() {

    private val session = sessionManager.session

    private val _reloadSession = MutableSharedFlow<Unit>()
    val reloadSession = _reloadSession
        .onStart { onEvent(MainActivityEvent.ReloadSession) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = Unit
        )

    fun onEvent(event: MainActivityEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when(event) {
                MainActivityEvent.ReloadSession -> reloadSession()
            }
        }
    }

    private suspend fun reloadSession() {
        if (session.value.isLogged.not()) return

        reloadAccountDetailsUseCase()
    }
}