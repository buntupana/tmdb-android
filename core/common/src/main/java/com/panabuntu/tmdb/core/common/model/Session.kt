package com.panabuntu.tmdb.core.common.model

import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import java.util.Locale

data class Session(
    val accessToken: String?,
    val sessionId: String?,
    val accountDetails: AccountDetails?
) {
    val isLogged: Boolean
        get() = accessToken.isNotNullOrBlank()

    val countryCode: String
        get() = accountDetails?.countryCode ?: Locale.getDefault().country
}
