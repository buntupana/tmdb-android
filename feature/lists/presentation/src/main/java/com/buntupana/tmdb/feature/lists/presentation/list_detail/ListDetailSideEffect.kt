package com.buntupana.tmdb.feature.lists.presentation.list_detail

sealed class ListDetailSideEffect {
    data object RefreshMediaItemList : ListDetailSideEffect()
}