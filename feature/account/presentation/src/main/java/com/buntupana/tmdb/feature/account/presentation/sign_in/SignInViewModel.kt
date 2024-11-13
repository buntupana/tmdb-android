package com.buntupana.tmdb.feature.account.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.buntupana.tmdb.core.domain.entity.onError
import com.buntupana.tmdb.core.domain.entity.onSuccess
import com.buntupana.tmdb.feature.account.domain.usecase.CreateAuthenticationUrlUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.CreateSessionUseCase
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

    private val navArgs = savedStateHandle.toRoute<SignInNav>()

    var state by mutableStateOf(SignInState())

    private val _sideEffect = Channel<SignInSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        when {
            navArgs.requestToken == null -> {
                onEvent(SignInEvent.CreateRequestToken)
            }
            navArgs.approved == true -> {
                onEvent(SignInEvent.CreateSession)
            }
            navArgs.approved == false -> {
                state = state.copy(isSignInError = true)
            }
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

        createSessionUseCase(navArgs.requestToken!!)
            .onError {
                state = state.copy(isLoading = false, isSignInError = true)
                Timber.d("createSession: error = $it")
            }
            .onSuccess {
                _sideEffect.send(SignInSideEffect.LoginSuccess)
            }
    }
}