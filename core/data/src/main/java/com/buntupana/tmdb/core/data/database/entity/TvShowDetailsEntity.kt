package com.buntupana.tmdb.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvShowDetailsEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val overview: String?,
    val tagline: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val genreList: String?,
    val firstAirDate: String?,
    val adult: Boolean?,
    val createdByList: String?,
    val episodeRunTimeList: String,
    val homepage: String?,
    val inProduction: Boolean?,
    val languageList: String?,
    val lastAirDate: String?,
    val lastEpisodeToAir: String?,
    val networkList: String,
    val nextEpisodeToAir: String?,
    val numberOfEpisodes: Int?,
    val numberOfSeasons: Int?,
    val originCountryList: String,
    val originalLanguageCode: String?,
    val originalName: String?,
    val popularity: Float?,
    val productionCompanyList: String,
    val productionCountryList: String,
    val seasonList: String?,
    val spokenLanguageList: String,
    val status: String?,
    val type: String?,
    val voteAverage: Float?,
    val voteCount: Int?,
    val videos: String?,
    val contentRatings: String?,
    val credits: String?,
    val recommendations: String?,
    val externalLinks: String?,
    val isFavorite: Boolean,
    val isWatchListed: Boolean,
    val userRating: Int?
)