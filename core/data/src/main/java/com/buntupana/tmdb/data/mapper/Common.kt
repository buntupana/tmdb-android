package com.buntupana.tmdb.data.mapper

import com.panabuntu.tmdb.core.common.entity.MediaType


fun getMediaType(value: String): MediaType {
    return when (value) {
        "tv" -> MediaType.TV_SHOW
        "movie" -> MediaType.MOVIE
        else -> {
            throw Exception("Impossible to cast media type $value")
        }
    }
}

fun getGender(value: Int?): com.panabuntu.tmdb.core.common.model.Gender {
    return when (value) {
        1 -> com.panabuntu.tmdb.core.common.model.Gender.FEMALE
        2 -> com.panabuntu.tmdb.core.common.model.Gender.MALE
        3 -> com.panabuntu.tmdb.core.common.model.Gender.NON_BINARY
        else -> com.panabuntu.tmdb.core.common.model.Gender.NOT_SPECIFIED
    }
}