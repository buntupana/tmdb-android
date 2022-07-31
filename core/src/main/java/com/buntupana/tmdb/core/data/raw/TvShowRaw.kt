package com.buntupana.tmdb.core.data.raw

import com.squareup.moshi.Json

data class TvShowRaw(
    val id: Long,
    val name: String,
    @field:Json(name = "original_name")
    val originalName: String,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "backdrop_path")
    val backdropPath: String?,
    @field:Json(name = "first_air_date")
    val firstAirDate: String?,
    @field:Json(name = "genre_ids")
    val genreIds: List<Int>,
    @field:Json(name = "origin_country")
    val originCountry: List<String>,
    @field:Json(name = "original_language")
    val originalLanguage: String,
    val overview: String,
    val popularity: Double,
    @field:Json(name = "vote_average")
    val voteAverage: Double,
    @field:Json(name = "vote_count")
    val voteCount: Int,
    @field:Json(name = "media_type")
    val mediaType: String
)