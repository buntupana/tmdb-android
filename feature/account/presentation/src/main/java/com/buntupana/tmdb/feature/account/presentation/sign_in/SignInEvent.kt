package com.buntupana.tmdb.feature.account.presentation.sign_in

sealed class SignInEvent {

    data object CreateRequestToken: SignInEvent()
    data object CreateSession: SignInEvent()
}