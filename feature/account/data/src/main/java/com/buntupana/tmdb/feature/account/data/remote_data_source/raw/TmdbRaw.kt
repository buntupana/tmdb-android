package com.buntupana.tmdb.feature.account.data.remote_data_source.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbRaw(
    @SerialName("avatar_path")
    val avatarPath: String
)