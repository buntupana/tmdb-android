package com.buntupana.tmdb.feature.seer.presentation.sign_in

import com.buntupana.tmdb.feature.seer.domain.model.SeerAuthType

data class SeerSignInState(
    val serverUrl: String = "",
    val username: String = "",
    val password: String = "",
    val authType: SeerAuthType = SeerAuthType.LOCAL,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = serverUrl.isNotBlank() && username.isNotBlank() && password.isNotBlank()
}
