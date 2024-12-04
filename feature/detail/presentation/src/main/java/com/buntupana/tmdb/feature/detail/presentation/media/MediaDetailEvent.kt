package com.buntupana.tmdb.feature.detail.presentation.media

sealed class MediaDetailEvent {
    data object GetMediaDetails: MediaDetailEvent()
    data object SetFavorite: MediaDetailEvent()
    data object SetWatchList: MediaDetailEvent()
    data class OnRatingSuccess(val rating: Int?): MediaDetailEvent()
}
