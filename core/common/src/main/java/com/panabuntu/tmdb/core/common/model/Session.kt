package com.panabuntu.tmdb.core.common.model

import com.panabuntu.tmdb.core.common.isNotNullOrBlank

data class Session(
    val sessionId: String?,
    val accountDetails: AccountDetails?
) {
    val isLogged: Boolean
        get() = sessionId.isNotNullOrBlank()
}
