package com.buntupana.tmdb.feature.account.presentation.lists

sealed class ListsEvent {

    data object GetLists: ListsEvent()
}