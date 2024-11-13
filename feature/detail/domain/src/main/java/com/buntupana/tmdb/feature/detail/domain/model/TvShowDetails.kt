package com.buntupana.tmdb.feature.detail.domain.model

import java.time.LocalDate


data class TvShowDetails(
    val id: Long,
    val title: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val trailerUrl: String,
    val overview: String,
    val tagLine: String,
    val releaseDate: LocalDate?,
    val userScore: Int,
    val runTime: Long,
    val genreList: List<String>,
    val creatorList: List<Person.Crew.TvShow>,
    val certificationList: List<Certification>,
    val videoList: List<Video>,
    val credits: CreditsTvShow,
    val seasonList: List<Season>,
    val lastEpisode: Episode?,
    val nextEpisode: Episode?,
    val isInAir: Boolean,
    val recommendationList: List<com.panabuntu.tmdb.core.common.model.MediaItem>,
)
