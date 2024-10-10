package com.buntupana.tmdb.feature.detail.presentation.episodes

sealed class EpisodesDetailEvent {
    data object GetEpisodesDetail: EpisodesDetailEvent()
}
