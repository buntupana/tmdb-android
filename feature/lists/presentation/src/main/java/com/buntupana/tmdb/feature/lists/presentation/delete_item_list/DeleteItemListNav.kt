package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

import com.buntupana.tmdb.core.ui.navigation.Routes
import com.panabuntu.tmdb.core.common.entity.MediaType

data class DeleteItemListNav(
    val itemId: String,
    val listId: Long,
    val mediaId: Long,
    val mediaName: String,
    val mediaType: MediaType
): Routes