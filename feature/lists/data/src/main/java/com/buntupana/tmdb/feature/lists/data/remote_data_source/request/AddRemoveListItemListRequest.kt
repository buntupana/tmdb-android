package com.buntupana.tmdb.feature.account.data.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRemoveListItemListRequest(
    @SerialName("items")
    val addItemList: List<AddItem>
)

@Serializable
data class AddItem(
    @SerialName("media_id")
    val mediaId: Long,
    @SerialName("media_type")
    val mediaType: String
)