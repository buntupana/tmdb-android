package com.buntupana.tmdb.feature.account.presentation.lists

sealed class ListsSideEffect {

    data object RefreshListItemList : ListsSideEffect()
}