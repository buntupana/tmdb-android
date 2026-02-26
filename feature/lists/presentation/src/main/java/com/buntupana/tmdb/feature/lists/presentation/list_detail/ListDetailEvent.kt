package com.buntupana.tmdb.feature.lists.presentation.list_detail

sealed class ListDetailEvent {

    data object GetDetails: ListDetailEvent()
}