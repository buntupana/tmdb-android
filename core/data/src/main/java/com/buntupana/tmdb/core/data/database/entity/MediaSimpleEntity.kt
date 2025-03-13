package com.buntupana.tmdb.core.data.database.entity

import com.panabuntu.tmdb.core.common.entity.MediaType

data class MediaSimpleEntity(
    val id: Long,
    val mediaType: MediaType,
    val title: String,
    val originalTitle: String,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val originalLanguageCode: String? = null,
    val adult: Boolean = false,
    val popularity: Float? = null,
    val releaseDate: String? = null,
    val video: Boolean? = null,
    val voteAverage: Float? = null,
    val voteCount: Int? = null
)