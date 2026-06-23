package com.buntupana.tmdb.feature.seerr.data.remote_data_source.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeerrRequestedSeasonStatusRaw(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("seasonNumber")
    val seasonNumber: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("updatedAt")
    val updatedAt: String
)