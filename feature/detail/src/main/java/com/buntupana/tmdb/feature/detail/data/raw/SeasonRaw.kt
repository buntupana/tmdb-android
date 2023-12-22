package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class SeasonRaw(
    val id: Long,
    val name: String,
    @field:Json(name = "air_date")
    val airDate: String?,
    @field:Json(name = "episode_count")
    val episodeCount: Int?,
    val overview: String?,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "season_number")
    val seasonNumber: Int?,
    @field:Json(name = "vote_average")
    val voteAverage: Float
)