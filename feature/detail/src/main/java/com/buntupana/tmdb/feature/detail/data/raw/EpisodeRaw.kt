package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class EpisodeRaw(
    val id: Long,
    @Json(name = "episode_number")
    val episodeNumber: Int?,
    @Json(name = "air_date")
    val airDate: String?,
    val name: String?,
    val overview: String?,
    @Json(name = "production_code")
    val productionCode: String?,
    val runtime: Int?,
    @Json(name = "season_number")
    val seasonNumber: Int?,
    @Json(name = "show_id")
    val showId: Long?,
    @Json(name = "still_path")
    val stillPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Float?,
    @Json(name = "episode_type")
    val episodeType: String?,
    @Json(name = "vote_count")
    val voteCount: Int?,
    @Json(name = "guest_stars")
    val guestStars: List<MediaCastMovieRaw>?,
    val crew: List<MediaCrewMovieRaw>?
)