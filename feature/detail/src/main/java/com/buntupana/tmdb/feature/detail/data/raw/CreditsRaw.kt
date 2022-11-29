package com.buntupana.tmdb.feature.detail.data.raw

data class CreditsRaw(
    val cast: List<MediaCastRaw>,
    val crew: List<MediaCrewRaw>
)