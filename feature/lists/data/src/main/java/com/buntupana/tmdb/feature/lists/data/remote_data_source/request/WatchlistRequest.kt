package com.buntupana.tmdb.feature.lists.data.remote_data_source.request

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