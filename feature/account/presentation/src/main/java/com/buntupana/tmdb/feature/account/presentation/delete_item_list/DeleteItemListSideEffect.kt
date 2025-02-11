package com.buntupana.tmdb.feature.account.presentation.delete_item_list

sealed class DeleteItemListSideEffect {

    data object DeleteSuccess : DeleteItemListSideEffect()
}