package com.buntupana.tmdb.feature.lists.presentation.delete_list

data class DeleteListState(
    val isLoading: Boolean = false,
    val listName: String
)