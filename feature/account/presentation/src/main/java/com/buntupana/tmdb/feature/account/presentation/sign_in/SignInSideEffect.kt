package com.buntupana.tmdb.feature.account.presentation.sign_in

sealed class SignInSideEffect {

    data class CreatedRequestTokenSuccess(val authenticationUrl: String) : SignInSideEffect()

    data object LoginSuccess : SignInSideEffect()
}