package com.buntupana.tmdb.feature.account.presentation.delete_list

data class DeleteListState(
    val isLoading: Boolean = false,
    val listName: String = ""
)