package com.buntupana.tmdb.feature.account.presentation.sign_out

sealed class SignOutSideEffect {
    data object SignOutSuccess: SignOutSideEffect()
}