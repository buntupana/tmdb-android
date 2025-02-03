package com.buntupana.tmdb.feature.account.presentation.create_list

sealed class CreateListEvent {

    data object CreateList: CreateListEvent()

    data object ClearListInfo: CreateListEvent()

    data class UpdateForm(
        val listName: String,
        val description: String,
        val isPublic: Boolean
    ): CreateListEvent()
}