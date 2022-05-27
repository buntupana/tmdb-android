package com.buntupana.tmdb.feature.discover.domain.model

data class TvShowItem(
    val id: Int,
    val name: String,
    val originalName: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val genreIds: List<Int>,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val firstAirDate: String,
    val originCountry: List<String>
)
