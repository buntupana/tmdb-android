package com.buntupana.tmdb.core.data.database.entity

import androidx.room.Entity

@Entity(tableName = "any_media", primaryKeys = ["id", "mediaType"])
data class AnyMediaEntity(
    val id: Long,
    val mediaType: String,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val originalLanguage: String? = null,
    val genreIdList: String? = null,
    val popularity: Float? = null,
    val voteAverage: Float? = null,
    val voteCount: Int? = null,
    // Movie fields
    val title: String? = null,
    val originalTitle: String? = null,
    val releaseDate: String? = null,
    val video: Boolean? = null,
    val adult: Boolean? = null,
    // TV_Show fields
    val originalName: String? = null,
    val firstAirDate: String? = null,
    val originCountryList: String? = null,
    // Person fields
    val name: String? = null,
    val profilePath: String? = null,
    val gender: Int? = null,
    val knownForDepartment: String? = null,
    val knownForList: String? = null,
)