package com.buntupana.tmdb.feature.seerr.presentation.sign_in

import com.buntupana.tmdb.feature.seerr.domain.model.SeerrAuthType

sealed class SeerrSignInEvent {
    data class ServerUrlChanged(val serverUrl: String) : SeerrSignInEvent()
    data class UsernameChanged(val username: String) : SeerrSignInEvent()
    data class PasswordChanged(val password: String) : SeerrSignInEvent()
    data class AuthTypeChanged(val authType: SeerrAuthType) : SeerrSignInEvent()
    data object Submit : SeerrSignInEvent()
}
