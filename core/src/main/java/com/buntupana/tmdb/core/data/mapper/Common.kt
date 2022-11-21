package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.domain.entity.MediaType

fun getMediaType(value: String): MediaType {
    return when (value) {
        "tv" -> MediaType.TV_SHOW
        "movie" -> MediaType.MOVIE
        else -> {
            throw Exception("Impossible to cast media type $value")
        }
    }
}