package com.buntupana.tmdb.feature.seer.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class SeerrSeasonStatusRaw(
    val seasonNumber: Int,
    val status: Int? = null
)