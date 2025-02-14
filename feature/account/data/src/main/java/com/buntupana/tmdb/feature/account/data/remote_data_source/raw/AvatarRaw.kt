package com.buntupana.tmdb.feature.account.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class AvatarRaw(
    val gravatar: GravatarRaw,
    val tmdb: TmdbRaw
)