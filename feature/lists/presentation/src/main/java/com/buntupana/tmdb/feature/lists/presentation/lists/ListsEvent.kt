package com.buntupana.tmdb.feature.lists.presentation.lists

sealed class ListsEvent {

    data object GetLists: ListsEvent()
}