package com.buntupana.tmdb.feature.account.presentation.sign_in

import com.buntupana.tmdb.core.ui.navigation.Routes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInNav(
    @SerialName("request_token")
    val requestToken: String? = null,
    val approved: Boolean? = false
): Routes