package com.buntupana.tmdb.feature.seerr.presentation.sign_in

import com.buntupana.tmdb.feature.seerr.domain.model.SeerrAuthType

data class SeerrSignInState(
    val serverUrl: String = "",
    val username: String = "",
    val password: String = "",
    val authType: SeerrAuthType = SeerrAuthType.LOCAL,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = serverUrl.isNotBlank() && username.isNotBlank() && password.isNotBlank()
}
