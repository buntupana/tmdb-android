package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

import com.buntupana.tmdb.core.ui.navigation.Route
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class DeleteItemListNav(
    val itemId: String,
    val listId: Long,
    val mediaId: Long,
    val mediaName: String,
    val mediaType: MediaType
): Route