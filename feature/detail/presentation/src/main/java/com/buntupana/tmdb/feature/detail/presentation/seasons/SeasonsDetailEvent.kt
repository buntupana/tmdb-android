package com.buntupana.tmdb.feature.detail.presentation.seasons

sealed class SeasonsDetailEvent {
    data object GetSeasons : SeasonsDetailEvent()
}