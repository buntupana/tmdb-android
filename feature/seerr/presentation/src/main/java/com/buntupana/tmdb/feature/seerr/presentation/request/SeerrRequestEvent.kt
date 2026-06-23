package com.buntupana.tmdb.feature.seerr.presentation.request

sealed class SeerrRequestEvent {
    data class OnSeasonToggle(val seasonNumber: Int) : SeerrRequestEvent()
    data object OnAllSeasonsToggle : SeerrRequestEvent()
    data object Submit : SeerrRequestEvent()
}