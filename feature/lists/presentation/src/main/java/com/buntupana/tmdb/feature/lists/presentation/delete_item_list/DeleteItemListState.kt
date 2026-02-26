package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

import com.panabuntu.tmdb.core.common.entity.MediaType

data class DeleteItemListState(
    val isLoading: Boolean = false,
    val mediaName: String,
    val mediaId: Long,
    val mediaType: MediaType,
    val listType: ListType
)