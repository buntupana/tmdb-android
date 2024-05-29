package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class SeasonDetailsRaw(
    val id: Int,
    val _id: String,
    @Json(name = "air_date")
    val airDate: String,
    val episodes: List<EpisodeRaw>,
    val name: String,
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "vote_average")
    val voteAverage: Float
)