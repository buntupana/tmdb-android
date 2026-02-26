package com.buntupana.tmdb.feature.account.presentation.sign_in

data class SignInState(
    val isLoading: Boolean = false,
    val isSignInError: Boolean = false,
)