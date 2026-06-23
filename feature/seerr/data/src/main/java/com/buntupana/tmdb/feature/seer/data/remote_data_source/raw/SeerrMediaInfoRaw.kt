package com.buntupana.tmdb.feature.seer.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class SeerrMediaInfoRaw(
    val id: Long? = null,
    val tmdbId: Long? = null,
    val mediaType: String? = null,
    val status: Int? = null,
    val requests: List<SeerrRequestRaw> = emptyList(),
    val seasons: List<SeerrSeasonStatusRaw> = emptyList()
)