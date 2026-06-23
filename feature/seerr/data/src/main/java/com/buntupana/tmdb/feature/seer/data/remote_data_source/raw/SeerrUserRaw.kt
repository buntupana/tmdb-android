package com.buntupana.tmdb.feature.seer.data.remote_data_source.raw

import kotlinx.serialization.Serializable

@Serializable
data class SeerrUserRaw(
    val id: Long,
    val email: String? = null,
    val displayName: String? = null,
    val username: String? = null,
    val plexUsername: String? = null,
    val jellyfinUsername: String? = null,
    val avatar: String? = null
)
