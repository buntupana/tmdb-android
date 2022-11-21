package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class PersonCrewRaw(
    val id: Long,
    val adult: Boolean,
    @field:Json(name = "backdrop_path")
    val backdropPath: String?,
    @field:Json(name = "credit_id")
    val creditId: String,
    val department: String,
    @field:Json(name = "episode_count")
    val episodeCount: Int,
    @field:Json(name = "first_air_date")
    val firstAirDate: String?,
    @field:Json(name = "genre_ids")
    val genreIds: List<Int>,
    val job: String?,
    @field:Json(name = "media_type")
    val mediaType: String,
    val name: String?,
    @field:Json(name = "origin_country")
    val originCountry: List<String>,
    @field:Json(name = "original_language")
    val originalLanguage: String,
    @field:Json(name = "original_name")
    val originalName: String,
    @field:Json(name = "original_title")
    val original_title: String,
    val overview: String,
    val popularity: Double,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean,
    @field:Json(name = "vote_average")
    val voteAverage: Double,
    @field:Json(name = "vote_count")
    val voteCount: Int
)