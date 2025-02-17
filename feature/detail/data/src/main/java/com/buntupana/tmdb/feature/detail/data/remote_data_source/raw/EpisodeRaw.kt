package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeRaw(
    val id: Long,
    @SerialName("episode_number")
    val episodeNumber: Int,
    @SerialName("air_date")
    val airDate: String? = null,
    val name: String,
    val overview: String? = null,
    @SerialName("production_code")
    val productionCode: String? = null,
    val runtime: Int? = null,
    @SerialName("season_number")
    val seasonNumber: Int,
    @SerialName("show_id")
    val showId: Long? = null,
    @SerialName("still_path")
    val stillPath: String? = null,
    @SerialName("vote_average")
    val voteAverage: Float? = null,
    @SerialName("episode_type")
    val episodeType: String? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null,
    @SerialName("guest_stars")
    val guestStars: List<MediaCastMovieRaw>? = null,
    val crew: List<MediaCrewMovieRaw>? = null
)