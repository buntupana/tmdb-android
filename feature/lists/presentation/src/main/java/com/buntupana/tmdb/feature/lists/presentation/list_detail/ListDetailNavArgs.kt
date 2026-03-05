package com.buntupana.tmdb.feature.lists.presentation.list_detail

data class ListDetailNavArgs(
    val listId: Long,
    val listName: String,
    val description: String?,
    val backdropUrl: String?
)