package com.buntupana.tmdb.feature.seer.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.seer.domain.model.SeerAuthType
import com.buntupana.tmdb.feature.seer.domain.usecase.SignInSeerJellyfinUseCase
import com.buntupana.tmdb.feature.seer.domain.usecase.SignInSeerLocalUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SeerSignInViewModel(
    private val signInLocalUseCase: SignInSeerLocalUseCase,
    private val signInJellyfinUseCase: SignInSeerJellyfinUseCase
) : ViewModel() {

    var state by mutableStateOf(SeerSignInState())
        private set

    private val _sideEffect = Channel<SeerSignInSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: SeerSignInEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        when (event) {
            is SeerSignInEvent.ServerUrlChanged -> state = state.copy(serverUrl = event.serverUrl)
            is SeerSignInEvent.UsernameChanged -> state = state.copy(username = event.username)
            is SeerSignInEvent.PasswordChanged -> state = state.copy(password = event.password)
            is SeerSignInEvent.AuthTypeChanged -> state = state.copy(authType = event.authType)
            SeerSignInEvent.Submit -> submit()
        }
    }

    private fun submit() {
        if (state.isLoading || !state.isFormValid) return
        viewModelScope.launch {
            state = state.copy(isLoading = true, isError = false, errorMessage = null)

            val result = when (state.authType) {
                SeerAuthType.LOCAL -> signInLocalUseCase(
                    serverUrl = state.serverUrl,
                    email = state.username,
                    password = state.password
                )

                SeerAuthType.JELLYFIN -> signInJellyfinUseCase(
                    serverUrl = state.serverUrl,
                    username = state.username,
                    password = state.password
                )
            }

            result
                .onError { error ->
                    state = state.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = error.name
                    )
                }
                .onSuccess {
                    state = state.copy(isLoading = false)
                    _sideEffect.send(SeerSignInSideEffect.LoginSuccess)
                }
        }
    }
}
