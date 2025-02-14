package com.buntupana.tmdb.feature.lists.presentation.create_update_list

sealed class CreateListEvent {

    data object CreateList: CreateListEvent()

    data class InitList(
        val listId: Long?,
        val listName: String,
        val description: String?,
        val isPublic: Boolean
    ): CreateListEvent()

    data class UpdateForm(
        val listName: String,
        val description: String,
        val isPublic: Boolean
    ): CreateListEvent()
}