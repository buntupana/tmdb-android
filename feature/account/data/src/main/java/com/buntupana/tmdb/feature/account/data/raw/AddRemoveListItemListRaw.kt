package com.buntupana.tmdb.feature.account.data.raw


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRemoveListItemListRaw(
    @SerialName("results")
    val results: List<Result>,
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String,
    @SerialName("success")
    val success: Boolean
)