package com.buntupana.tmdb.feature.lists.presentation.delete_list

sealed class DeleteListEvent {

    data object ConfirmDeleteList : DeleteListEvent()
}