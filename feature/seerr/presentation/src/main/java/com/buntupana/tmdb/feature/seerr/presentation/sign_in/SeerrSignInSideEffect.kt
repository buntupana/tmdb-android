package com.buntupana.tmdb.feature.seerr.presentation.sign_in

sealed class SeerrSignInSideEffect {
    data object LoginSuccess : SeerrSignInSideEffect()
}
