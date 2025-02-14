package com.buntupana.tmdb.feature.account.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountDetailsRaw(
    val id: Long,
    val username: String,
    val name: String,
    val avatar: AvatarRaw,
    @SerialName("include_adult")
    val includeAdult: Boolean,
    val iso_3166_1: String,
    val iso_639_1: String
)