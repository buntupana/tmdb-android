package com.buntupana.tmdb.core.data.raw

import com.squareup.moshi.Json

data class KnownFor(
    val id: Long,
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "original_name")
    val originalName: String?,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String?,
    val name: String?,
    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)