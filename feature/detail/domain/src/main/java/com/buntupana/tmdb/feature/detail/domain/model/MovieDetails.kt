package com.buntupana.tmdb.feature.detail.domain.model

import java.time.LocalDate


data class MovieDetails(
    val id: Long,
    val title: String,
    val originalTitle: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val trailerUrl: String,
    val overview: String,
    val tagLine: String,
    val releaseDate: LocalDate?,
    val userScore: Int?,
    val voteCount: Int,
    val runTime: Long?,
    val genreList: List<String>,
    val productionCountryCodeList: List<String>,
    val releaseDateList: List<ReleaseDate>,
    val videoList: List<Video>,
    val credits: Credits,
    val recommendationList: List<com.panabuntu.tmdb.core.common.model.MediaItem>,
    val isFavorite: Boolean,
    val isWatchListed: Boolean,
    val userRating: Int?,
    val isRateable: Boolean,
    val status: String?,
    val originalLanguage: String?,
    val budget: Long?,
    val revenue: Long?,
    val externalLinkList: List<ExternalLink>
)
