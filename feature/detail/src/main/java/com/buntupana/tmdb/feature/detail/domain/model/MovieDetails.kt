package com.buntupana.tmdb.feature.detail.domain.model

import org.threeten.bp.LocalDate

data class MovieDetails(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val trailerUrl: String,
    val overview: String,
    val tagLine: String,
    val releaseDate: LocalDate?,
    val userScore: Int,
    val runTime: Long,
    val genreList: List<String>,
    val productionCountryCodeList: List<String>,
    val releaseDateList: List<ReleaseDate>,
    val videoList: List<Video>,
    val credits: Credits
)
