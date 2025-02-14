package com.buntupana.tmdb.feature.lists.presentation.lists

sealed class ListsSideEffect {

    data object RefreshListItemList : ListsSideEffect()
}