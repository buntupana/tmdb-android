package com.buntupana.tmdb.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetailsEntity(
    @PrimaryKey
    val id: Long,
    val adult: Boolean,
    val backdropPath: String?,
    val belongsToCollection: String?,
    val budget: Long,
    val genreList: String,
    val homepage: String,
    val imdbId: String?,
    val originalLanguageCode: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Float,
    val posterPath: String?,
    val productionCompanyList: String,
    val productionCountryList: String,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Long,
    val spokenLanguageList: String,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean?,
    val voteAverage: Float,
    val videos: String?,
    val releaseDates: String?,
    val voteCount: Int?,
    val credits: String?,
    val recommendations: String?,
    val externalLinks: String?,
    val isFavorite: Boolean,
    val isWatchListed: Boolean,
    val userRating: Int?
)