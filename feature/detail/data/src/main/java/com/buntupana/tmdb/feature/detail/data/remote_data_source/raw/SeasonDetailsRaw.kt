package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonDetailsRaw(
    val id: Int,
    val _id: String,
    @SerialName("air_date")
    val airDate: String?,
    val episodes: List<EpisodeRaw>,
    val name: String,
    val overview: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("season_number")
    val seasonNumber: Int,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("account_states")
    val accountStates: SeasonDetailsAccountStates? = null
)