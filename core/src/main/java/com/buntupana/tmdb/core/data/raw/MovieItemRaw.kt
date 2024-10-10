package com.buntupana.tmdb.core.data.raw

import com.squareup.moshi.Json

data class MovieItemRaw(
    val id: Long,
    val title: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    val adult: Boolean,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val popularity: Float?,
    @Json(name = "release_date")
    val releaseDate: String?,
    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Float?,
    @Json(name = "vote_count")
    val voteCount: Int?
)