package com.buntupana.tmdb.feature.account.presentation.create_update_list

data class CreateListState(
    val isLoading: Boolean = false,
    val isNewList: Boolean = true,
    val listName: String = "",
    val description: String = "",
    val isPublic: Boolean = true
)