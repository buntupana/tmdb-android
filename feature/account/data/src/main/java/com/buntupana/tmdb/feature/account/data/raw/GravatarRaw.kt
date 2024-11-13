package com.buntupana.tmdb.feature.account.data.raw

import kotlinx.serialization.Serializable

@Serializable
data class GravatarRaw(
    val hash: String
)