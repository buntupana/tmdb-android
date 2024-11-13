package com.buntupana.tmdb.feature.account.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbRaw(
    @SerialName("avatar_path")
    val avatarPath: String
)