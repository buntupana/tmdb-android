package com.buntupana.tmdb.feature.account.data.raw

import kotlinx.serialization.Serializable

@Serializable
data class AvatarRaw(
    val gravatar: GravatarRaw,
    val tmdb: TmdbRaw
)