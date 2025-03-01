package com.buntupana.tmdb.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val backdropPath: String?,
    val originalLanguageCode: String?,
    val originalTitle: String,
    val overview: String?,
    val popularity: Float?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val video: Boolean?,
    val voteAverage: Float?,
    val voteCount: Int?,
    val genreList: String? = null,
    val userRating: Int? = null,
    val belongsToCollection: String? = null,
    val budget: Long? = null,
    val homepage: String? = null,
    val imdbId: String? = null,
    val productionCompanyList: String? = null,
    val productionCountryList: String? = null,
    val revenue: Long? = null,
    val runtime: Long? = null,
    val spokenLanguageList: String? = null,
    val status: String? = null,
    val tagline: String? = null,
    val videos: String? = null,
    val releaseDates: String? = null,
    val credits: String? = null,
    val recommendations: String? = null,
    val externalLinks: String? = null,
    @ColumnInfo(defaultValue = "false")
    val isFavorite: Boolean = false,
    @ColumnInfo(defaultValue = "false")
    val isWatchListed: Boolean = false,
)