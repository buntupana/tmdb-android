package com.buntupana.tmdb.feature.seerr.data.remote_data_source.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeerrRequestRaw(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("is4k")
    val is4k: Boolean,
    @SerialName("seasonCount")
    val seasonCount: Int,
    @SerialName("seasons")
    val seasons: List<SeerrRequestedSeasonStatusRaw>,
    @SerialName("status")
    val status: Int,
    @SerialName("type")
    val type: String,
    @SerialName("updatedAt")
    val updatedAt: String
)