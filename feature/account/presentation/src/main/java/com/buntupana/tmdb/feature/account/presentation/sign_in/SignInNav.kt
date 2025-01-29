package com.buntupana.tmdb.feature.account.presentation.sign_in

import com.buntupana.tmdb.core.ui.navigation.Routes
import kotlinx.serialization.Serializable

@Serializable
data class SignInNav(
    val approved: Boolean? = false
): Routes