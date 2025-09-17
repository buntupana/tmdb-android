package com.buntupana.tmdb.feature.account.presentation.sign_in

import com.buntupana.tmdb.core.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class SignInRoute(
    val approved: Boolean? = false
): Route