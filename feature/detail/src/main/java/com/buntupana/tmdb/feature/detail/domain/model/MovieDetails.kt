package com.buntupana.tmdb.feature.detail.domain.model

import org.threeten.bp.LocalDate

data class MovieDetails(
    val id: Long,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val overview: String,
    val tagLine: String,
    val releaseDate: LocalDate,
    val userScore: Int,
    val runTime: Int,
    val genreList: List<String>
)
