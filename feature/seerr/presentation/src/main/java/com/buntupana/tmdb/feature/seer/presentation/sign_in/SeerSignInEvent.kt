package com.buntupana.tmdb.feature.seer.presentation.sign_in

import com.buntupana.tmdb.feature.seer.domain.model.SeerAuthType

sealed class SeerSignInEvent {
    data class ServerUrlChanged(val serverUrl: String) : SeerSignInEvent()
    data class UsernameChanged(val username: String) : SeerSignInEvent()
    data class PasswordChanged(val password: String) : SeerSignInEvent()
    data class AuthTypeChanged(val authType: SeerAuthType) : SeerSignInEvent()
    data object Submit : SeerSignInEvent()
}
