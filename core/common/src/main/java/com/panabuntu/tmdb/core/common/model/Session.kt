package com.panabuntu.tmdb.core.common.model

import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

data class Session(
    val accessToken: String?,
    val sessionId: String?,
    val accountDetails: AccountDetails?
) {
    val isLogged: Boolean
        get() = accessToken.isNotNullOrBlank()
}
