package com.buntupana.tmdb.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.panabuntu.tmdb.core.common.entity.MediaType

@Entity(tableName = "media", primaryKeys = ["id", "mediaType"])
data class MediaEntity(
    // Common
    val id: Long,
    val mediaType: MediaType,
    val title: String,
    val originalTitle: String,
    val adult: Boolean,
    val backdropPath: String?,
    val originalLanguageCode: String?,
    val overview: String?,
    val popularity: Float?,
    val posterPath: String?,
    val voteAverage: Float?,
    val voteCount: Int?,
    val releaseDate: String? = null,
    val genreList: String? = null,
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
    // Movie
    val video: Boolean? = null,
    val belongsToCollection: String? = null,
    val budget: Long? = null,
    val imdbId: String? = null,
    val revenue: Long? = null,
    val runtime: Long? = null,
    val releaseDates: String? = null,
    // Tv Show
    val originCountryList: String? = null,
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
)