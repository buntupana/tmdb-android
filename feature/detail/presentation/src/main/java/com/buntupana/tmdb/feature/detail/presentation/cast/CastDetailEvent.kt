package com.buntupana.tmdb.feature.detail.presentation.cast

sealed class CastDetailEvent {
    data object GetCredits : CastDetailEvent()
}