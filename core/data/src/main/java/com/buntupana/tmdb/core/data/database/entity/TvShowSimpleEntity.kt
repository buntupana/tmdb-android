package com.buntupana.tmdb.core.data.database.entity

data class TvShowSimpleEntity(
    val id: Long,
    val name: String,
    val originalName: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val firstAirDate: String? = null,
    val originCountryList: String? = null,
    val originalLanguageCode: String? = null,
    val overview: String? = null,
    val popularity: Float? = null,
    val voteAverage: Float? = null,
    val voteCount: Int? = null
)