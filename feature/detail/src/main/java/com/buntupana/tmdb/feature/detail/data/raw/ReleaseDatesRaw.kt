package com.buntupana.tmdb.feature.detail.data.raw

data class ReleaseDatesRaw(
    val id: Long,
    val results: List<ReleaseDateCountry>
)