package com.buntupana.tmdb.feature.account.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class GravatarRaw(
    val hash: String
)