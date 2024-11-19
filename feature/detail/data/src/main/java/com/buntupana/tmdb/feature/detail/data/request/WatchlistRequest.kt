package com.buntupana.tmdb.feature.detail.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchlistRequest(
    @SerialName("media_id")
    val mediaId: Long,
    @SerialName("media_type")
    val mediaType: String,
    val watchlist: Boolean
)