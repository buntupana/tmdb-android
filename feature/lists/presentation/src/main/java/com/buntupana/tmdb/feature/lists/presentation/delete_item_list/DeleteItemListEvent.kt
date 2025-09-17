package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

sealed class DeleteItemListEvent {

    data object ConfirmDelete : DeleteItemListEvent()
}