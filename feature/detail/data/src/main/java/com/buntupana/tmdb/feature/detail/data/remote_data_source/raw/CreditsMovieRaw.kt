package com.buntupana.tmdb.feature.detail.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class CreditsMovieRaw(
    val cast: List<MediaCastMovieRaw>,
    val crew: List<MediaCrewMovieRaw>
)