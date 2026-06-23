package com.buntupana.tmdb.feature.seer.presentation.request

sealed class SeerrRequestSideEffect {
    data object Dismiss : SeerrRequestSideEffect()
}