package com.buntupana.tmdb.core.data.raw

import com.squareup.moshi.Json

data class KnownFor(
    val id: Long,
    val adult: Boolean,
    @field:Json(name = "backdrop_path")
    val backdropPath: String,
    @field:Json(name = "genre_ids")
    val genreIds: List<Int>,
    @field:Json(name = "media_type")
    val mediaType: String,
    @field:Json(name = "original_language")
    val originalLanguage: String,
    @field:Json(name = "original_title")
    val originalTitle: String?,
    @field:Json(name = "original_name")
    val originalName: String?,
    val overview: String,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "release_date")
    val releaseDate: String,
    val title: String?,
    val name: String?,
    val video: Boolean,
    @field:Json(name = "vote_average")
    val voteAverage: Double,
    @field:Json(name = "vote_count")
    val voteCount: Int
)