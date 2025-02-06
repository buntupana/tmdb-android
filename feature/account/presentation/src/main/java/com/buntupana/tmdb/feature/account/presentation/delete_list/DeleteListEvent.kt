package com.buntupana.tmdb.feature.account.presentation.delete_list

sealed class DeleteListEvent {

    data class Init(val deleteListNav: DeleteListNav) : DeleteListEvent()

    data object ConfirmDeleteList : DeleteListEvent()
}