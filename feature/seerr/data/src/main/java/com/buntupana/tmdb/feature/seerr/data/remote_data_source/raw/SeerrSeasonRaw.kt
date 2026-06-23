package com.buntupana.tmdb.feature.seerr.data.remote_data_source.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeerrSeasonRaw(
    @SerialName("id")
    val id: Int,
    @SerialName("airDate")
    val airDate: String?,
    @SerialName("episodeCount")
    val episodeCount: Int,
    @SerialName("name")
    val name: String,
    @SerialName("overview")
    val overview: String?,
    @SerialName("posterPath")
    val posterPath: String?,
    @SerialName("seasonNumber")
    val seasonNumber: Int
)