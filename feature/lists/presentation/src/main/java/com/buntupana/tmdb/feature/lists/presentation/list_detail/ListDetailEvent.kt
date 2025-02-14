package com.buntupana.tmdb.feature.lists.presentation.list_detail

import com.panabuntu.tmdb.core.common.entity.MediaType

sealed class ListDetailEvent {

    data object GetDetails: ListDetailEvent()

    data class SuccessDeleteItemList(
        val itemId: String,
        val mediaType: MediaType
    ): ListDetailEvent()

    data class CancelDeleteItemList(
        val itemId: String,
        val mediaType: MediaType
    ): ListDetailEvent()
}