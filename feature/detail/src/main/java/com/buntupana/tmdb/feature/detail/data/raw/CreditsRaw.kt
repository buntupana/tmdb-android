package com.buntupana.tmdb.feature.detail.data.raw

data class CreditsRaw(
    val id: Int,
    val cast: List<CastRaw>,
    val crew: List<CrewRaw>
)