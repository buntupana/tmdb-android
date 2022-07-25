package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class LastEpisodeToAir(
    @field:Json(name = "air_date")
    val airDate: String?,
    @field:Json(name = "episode_number")
    val episodeNumber: Int?,
    val id: Long,
    val name: String?,
    val overview: String?,
    @field:Json(name = "production_code")
    val productionCode: String?,
    val runtime: Int?,
    @field:Json(name = "season_number")
    val seasonNumber: Int?,
    @field:Json(name = "show_id")
    val showId: Int?,
    @field:Json(name = "still_path")
    val stillPath: String?,
    @field:Json(name = "vote_average")
    val voteAverage: Double?,
    @field:Json(name = "vote_count")
    val voteCount: Int?
)