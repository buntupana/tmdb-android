package com.buntupana.tmdb.feature.account.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.account.domain.usecase.CreateAuthenticationUrlUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.CreateSessionUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createAuthenticationUrlUseCase: CreateAuthenticationUrlUseCase,
    private val createSessionUseCase: CreateSessionUseCase
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<SignInNav>()

    var state by mutableStateOf(SignInState())
        private set

    private val _sideEffect = Channel<SignInSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        when(navArgs.approved) {
            true -> {
                onEvent(SignInEvent.CreateSession)
            }
            false -> {
                onEvent(SignInEvent.CreateRequestToken)
            }

            null -> {}
        }
    }

    fun onEvent(event: SignInEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                SignInEvent.CreateRequestToken -> createToken()
                SignInEvent.CreateSession -> createSession()
            }
        }
    }

    private suspend fun createToken() {
        state = state.copy(isLoading = true, isSignInError = false)

        createAuthenticationUrlUseCase()
            .onError {
                state = state.copy(isLoading = false, isSignInError = true)
            }
            .onSuccess { authenticationUrl ->
                state = state.copy(isLoading = false)
                _sideEffect.send(SignInSideEffect.CreatedRequestTokenSuccess(authenticationUrl))
            }
    }

    private suspend fun createSession() {
        state = state.copy(isLoading = true, isSignInError = false)

        createSessionUseCase()
            .onError {
                state = state.copy(isLoading = false, isSignInError = true)
                Timber.d("createSession: error = $it")
            }
            .onSuccess {
                _sideEffect.send(SignInSideEffect.LoginSuccess)
            }
    }
}