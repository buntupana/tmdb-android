package com.buntupana.tmdb.feature.account.presentation.sign_out

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.account.domain.usecase.SignOutUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class SignOutViewModel(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    var state by mutableStateOf(SignOutState())
        private set

    private val _sideEffect = Channel<SignOutSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: SignOutEvent) {
        Timber.d("onEvent() called with: event = []")
        viewModelScope.launch {
            when (event) {
                SignOutEvent.SignOut -> signOut()
            }
        }
    }

    private suspend fun signOut() {
        state = state.copy(isLoading = true)
        signOutUseCase()
            .onError {
                state = state.copy(isLoading = false)
            }
            .onSuccess {
                _sideEffect.send(SignOutSideEffect.SignOutSuccess)
            }
    }
}