package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class SeasonRaw(
    val id: Long,
    val name: String,
    @Json(name = "air_date")
    val airDate: String?,
    @Json(name = "episode_count")
    val episodeCount: Int?,
    val overview: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "season_number")
    val seasonNumber: Int?,
    @Json(name = "vote_average")
    val voteAverage: Float
)