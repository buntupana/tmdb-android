package com.buntupana.tmdb.feature.seer.presentation.sign_in

sealed class SeerSignInSideEffect {
    data object LoginSuccess : SeerSignInSideEffect()
}
