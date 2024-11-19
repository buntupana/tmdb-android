package com.buntupana.tmdb.feature.detail.domain.model

data class MediaAccountState(
    val id: Long,
    val isFavorite: Boolean,
    val userRating: Int?,
    val isWatchisted: Boolean
)
