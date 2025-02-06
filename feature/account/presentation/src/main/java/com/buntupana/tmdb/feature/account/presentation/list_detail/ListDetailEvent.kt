package com.buntupana.tmdb.feature.account.presentation.list_detail

sealed class ListDetailEvent {

    data object GetDetails: ListDetailEvent()
}