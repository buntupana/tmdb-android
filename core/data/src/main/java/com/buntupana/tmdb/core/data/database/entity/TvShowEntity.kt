package com.buntupana.tmdb.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_show")
data class TvShowEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val firstAirDate: String?,
    val originCountryList: String,
    val originalLanguageCode: String?,
    val originalName: String?,
    val popularity: Float?,
    val voteAverage: Float?,
    val voteCount: Int?,
    val genreList: String? = null,
    val contentRatings: String? = null,
    val seasonList: String? = null,
    val type: String? = null,
    val nextEpisodeToAir: String? = null,
    val numberOfEpisodes: Int? = null,
    val numberOfSeasons: Int? = null,
    val createdByList: String? = null,
    val episodeRunTimeList: String? = null,
    @ColumnInfo(defaultValue = "false")
    val inProduction: Boolean? = false,
    val languageList: String? = null,
    val lastAirDate: String? = null,
    val lastEpisodeToAir: String? = null,
    val networkList: String? = null,
    val userRating: Int? = null,
    val homepage: String? = null,
    val productionCompanyList: String? = null,
    val productionCountryList: String? = null,
    val spokenLanguageList: String? = null,
    val status: String? = null,
    val tagline: String? = null,
    val videos: String? = null,
    val credits: String? = null,
    val recommendations: String? = null,
    val externalLinks: String? = null,
    @ColumnInfo(defaultValue = "false")
    val isFavorite: Boolean = false,
    @ColumnInfo(defaultValue = "false")
    val isWatchListed: Boolean = false,
    @ColumnInfo(defaultValue = "false")
    val adult: Boolean = false,
)