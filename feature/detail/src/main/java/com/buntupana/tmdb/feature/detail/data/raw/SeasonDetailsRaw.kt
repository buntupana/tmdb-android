package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class SeasonDetailsRaw(
    val id: Int,
    val _id: String,
    @field:Json(name = "air_date")
    val airDate: String,
    val episodes: List<EpisodeRaw>,
    val name: String,
    val overview: String?,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "season_number")
    val seasonNumber: Int,
    @field:Json(name = "vote_average")
    val voteAverage: Float
)