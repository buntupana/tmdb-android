package com.buntupana.tmdb.feature.seerr.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.seerr.domain.model.SeerrAuthType
import com.buntupana.tmdb.feature.seerr.domain.usecase.SignInSeerLocalUseCase
import com.buntupana.tmdb.feature.seerr.domain.usecase.SignInSeerrJellyfinUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class SeerrSignInViewModel(
    private val signInLocalUseCase: SignInSeerLocalUseCase,
    private val signInJellyfinUseCase: SignInSeerrJellyfinUseCase
) : ViewModel() {

    var state by mutableStateOf(SeerrSignInState())
        private set

    private val _sideEffect = Channel<SeerrSignInSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: SeerrSignInEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        when (event) {
            is SeerrSignInEvent.ServerUrlChanged -> state = state.copy(serverUrl = event.serverUrl)
            is SeerrSignInEvent.UsernameChanged -> state = state.copy(username = event.username)
            is SeerrSignInEvent.PasswordChanged -> state = state.copy(password = event.password)
            is SeerrSignInEvent.AuthTypeChanged -> state = state.copy(authType = event.authType)
            SeerrSignInEvent.Submit -> submit()
        }
    }

    private fun submit() {
        if (state.isLoading || !state.isFormValid) return
        viewModelScope.launch {
            state = state.copy(isLoading = true, isError = false, errorMessage = null)

            val result = when (state.authType) {
                SeerrAuthType.LOCAL -> signInLocalUseCase(
                    serverUrl = state.serverUrl,
                    email = state.username,
                    password = state.password
                )

                SeerrAuthType.JELLYFIN -> signInJellyfinUseCase(
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
                    _sideEffect.send(SeerrSignInSideEffect.LoginSuccess)
                }
        }
    }
}
