package com.buntupana.tmdb.feature.account.presentation.sign_out

sealed class SignOutEvent {
    data object SignOut: SignOutEvent()
}