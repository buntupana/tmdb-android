package com.buntupana.tmdb.feature.account.data.remote_data_source.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteRequest(
    @SerialName("media_id")
    val mediaId: Long,
    @SerialName("media_type")
    val mediaType: String,
    val favorite: Boolean
)