package com.buntupana.tmdb.feature.detail.data.raw

data class FilmographyRaw(
    val id: Long,
    val cast: List<PersonCastRaw>?,
    val crew: List<PersonCrewRaw>?
)