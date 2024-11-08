package com.buntupana.tmdb.feature.detail.data.raw

data class CreditsMovieRaw(
    val cast: List<MediaCastMovieRaw>,
    val crew: List<MediaCrewMovieRaw>
)