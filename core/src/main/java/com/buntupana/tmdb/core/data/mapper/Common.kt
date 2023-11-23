package com.buntupana.tmdb.core.data.mapper

import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.core.domain.model.Gender

fun getMediaType(value: String): MediaType {
    return when (value) {
        "tv" -> MediaType.TV_SHOW
        "movie" -> MediaType.MOVIE
        else -> {
            throw Exception("Impossible to cast media type $value")
        }
    }
}

fun getGender(value: Int?): Gender {
    return when (value) {
        1 -> Gender.FEMALE
        2 -> Gender.MALE
        3 -> Gender.NON_BINARY
        else -> Gender.NOT_SPECIFIED
    }
}