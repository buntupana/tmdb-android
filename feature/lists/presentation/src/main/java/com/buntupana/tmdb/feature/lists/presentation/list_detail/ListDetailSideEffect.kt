package com.buntupana.tmdb.feature.lists.presentation.list_detail

sealed class ListDetailSideEffect {

    data object RefreshMediaItemList : ListDetailSideEffect()

    data class UpdateItemRevealed(
        val itemId: String,
        val isRevealed: Boolean
    ) : ListDetailSideEffect()

    data object NavigateBack: ListDetailSideEffect()
}