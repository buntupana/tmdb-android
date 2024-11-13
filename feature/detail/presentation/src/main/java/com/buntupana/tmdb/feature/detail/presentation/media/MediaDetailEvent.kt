package com.buntupana.tmdb.feature.detail.presentation.media

sealed class MediaDetailEvent {
    data object GetMediaDetails: MediaDetailEvent()
}
