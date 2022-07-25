package com.buntupana.tmdb.feature.detail.domain.model

import org.threeten.bp.LocalDate

sealed class MediaDetails(
    open val id: Long,
    open val title: String,
    open val posterUrl: String,
    open val backdropUrl: String,
    open val overview: String,
    open val tagLine: String,
    open val releaseDate: LocalDate,
    open val userScore: Int,
    open val runTime: Int,
    open val genreList: List<String>
) {

    data class MovieDetails(
        override val id: Long,
        override val title: String,
        override val posterUrl: String,
        override val backdropUrl: String,
        override val overview: String,
        override val tagLine: String,
        override val releaseDate: LocalDate,
        override val userScore: Int,
        override val runTime: Int,
        override val genreList: List<String>
    ) : MediaDetails(
        id,
        title,
        posterUrl,
        backdropUrl,
        overview,
        tagLine,
        releaseDate,
        userScore,
        runTime,
        genreList
    )

    data class TvShowDetails(
        override val id: Long,
        override val title: String,
        override val posterUrl: String,
        override val backdropUrl: String,
        override val overview: String,
        override val tagLine: String,
        override val releaseDate: LocalDate,
        override val userScore: Int,
        override val runTime: Int,
        override val genreList: List<String>
    ) : MediaDetails(
        id,
        title,
        posterUrl,
        backdropUrl,
        overview,
        tagLine,
        releaseDate,
        userScore,
        runTime,
        genreList
    )
}
