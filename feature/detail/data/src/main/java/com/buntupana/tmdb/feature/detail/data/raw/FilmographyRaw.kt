package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.Serializable

@Serializable
data class FilmographyRaw(
    val cast: List<PersonCastRaw>?,
    val crew: List<PersonCrewRaw>?
)