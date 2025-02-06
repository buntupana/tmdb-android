package com.buntupana.tmdb.feature.account.presentation.list_detail

sealed class ListDetailSideEffect {
    data object RefreshMediaItemList : ListDetailSideEffect()
}