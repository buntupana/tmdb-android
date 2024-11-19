package com.buntupana.tmdb.feature.detail.data.mapper

import com.buntupana.tmdb.feature.detail.data.raw.MediaAccountStateRaw
import com.buntupana.tmdb.feature.detail.domain.model.MediaAccountState

fun MediaAccountStateRaw.toModel(): MediaAccountState {
    return MediaAccountState(
        id = id,
        isFavorite = favorite,
        userRating = (rated?.value?.times(10))?.toInt(),
        isWatchisted = watchlist
    )
}