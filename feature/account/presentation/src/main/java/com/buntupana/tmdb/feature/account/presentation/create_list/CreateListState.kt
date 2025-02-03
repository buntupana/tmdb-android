package com.buntupana.tmdb.feature.account.presentation.create_list

data class CreateListState(
    val isLoading: Boolean = false,
    val listName: String = "",
    val description: String = "",
    val isPublic: Boolean = true
)