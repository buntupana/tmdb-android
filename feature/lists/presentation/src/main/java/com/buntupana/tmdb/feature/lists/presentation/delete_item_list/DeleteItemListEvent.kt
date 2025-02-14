package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

sealed class DeleteItemListEvent {

    data class Init(val deleteItemListNav: DeleteItemListNav) : DeleteItemListEvent()

    data object ConfirmDelete : DeleteItemListEvent()
}