package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

sealed class DeleteItemListSideEffect {

    data object DeleteSuccess : DeleteItemListSideEffect()
}