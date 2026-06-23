package com.buntupana.tmdb.feature.seerr.presentation.request

sealed class SeerrRequestSideEffect {
    data object Dismiss : SeerrRequestSideEffect()
}