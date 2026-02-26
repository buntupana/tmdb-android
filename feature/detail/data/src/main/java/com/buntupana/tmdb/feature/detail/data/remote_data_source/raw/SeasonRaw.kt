package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonRaw(
    val id: Long,
    val name: String,
    @SerialName("air_date")
    val airDate: String? = null,
    @SerialName("episode_count")
    val episodeCount: Int? = null,
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("season_number")
    val seasonNumber: Int? = null,
    @SerialName("vote_average")
    val voteAverage: Float
)