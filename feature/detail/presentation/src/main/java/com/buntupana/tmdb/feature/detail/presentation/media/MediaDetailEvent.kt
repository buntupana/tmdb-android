package com.buntupana.tmdb.feature.detail.presentation.media

sealed class MediaDetailEvent {
    object GetMediaDetails: MediaDetailEvent()
}
