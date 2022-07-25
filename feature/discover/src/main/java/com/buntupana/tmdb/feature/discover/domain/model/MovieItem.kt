package com.buntupana.tmdb.feature.discover.domain.model

data class MovieItem(
    val id: Long,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val originalLanguage: String,
    val genreIds: List<Int>,
    val popularity: Double,
    val voteAverage: Int,
    val voteCount: Int,
    val releaseDate: String,
    val video: Boolean,
    val adult: Boolean
)
