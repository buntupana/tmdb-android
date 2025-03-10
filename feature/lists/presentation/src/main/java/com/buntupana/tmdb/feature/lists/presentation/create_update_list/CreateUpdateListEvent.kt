package com.buntupana.tmdb.feature.lists.presentation.create_update_list

sealed class CreateUpdateListEvent {

    data object CreateUpdateList: CreateUpdateListEvent()

    data class UpdateForm(
        val listName: String,
        val description: String,
        val isPublic: Boolean
    ): CreateUpdateListEvent()
}