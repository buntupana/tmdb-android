package com.buntupana.tmdb.feature.lists.presentation.list_detail

import com.buntupana.tmdb.core.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class ListDetailNav(
    val listId: Long,
    val listName: String,
    val description: String?,
    val backdropUrl: String?
): Route