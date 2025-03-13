package com.buntupana.tmdb.feature.lists.presentation.create_update_list

data class CreateUpdateListState(
    val isLoading: Boolean = false,
    val isNewList: Boolean,
    val listName: String,
    val description: String,
    val isPublic: Boolean
)