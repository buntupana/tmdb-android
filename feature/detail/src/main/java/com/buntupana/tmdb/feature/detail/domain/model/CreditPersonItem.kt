package com.buntupana.tmdb.feature.detail.domain.model

import java.time.LocalDate


sealed class CreditPersonItem(
    open val id: Long,
    open val title: String,
    open val department: String,
    open val role: String,
    open val posterUrl: String?,
    open val backdropUrl: String?,
    open val popularity: Double,
    open val userScore: Int,
    open val voteCount: Int,
    open val releaseDate: LocalDate?
) {

    data class Movie(
        override val id: Long,
        override val title: String,
        override val department: String,
        override val role: String,
        override val posterUrl: String?,
        override val backdropUrl: String?,
        override val popularity: Double,
        override val userScore: Int,
        override val voteCount: Int,
        override val releaseDate: LocalDate?,
        val castOrder: Int
    ) : CreditPersonItem(
        id,
        title,
        department,
        role,
        posterUrl,
        backdropUrl,
        popularity,
        userScore,
        voteCount,
        releaseDate
    )

    data class TvShow(
        override val id: Long,
        override val title: String,
        override val department: String,
        override val role: String,
        override val posterUrl: String?,
        override val backdropUrl: String?,
        override val popularity: Double,
        override val userScore: Int,
        override val voteCount: Int,
        override val releaseDate: LocalDate?,
        val episodeCount: Int
    ) : CreditPersonItem(
        id,
        title,
        department,
        role,
        posterUrl,
        backdropUrl,
        popularity,
        userScore,
        voteCount,
        releaseDate
    )
}
