package com.buntupana.tmdb.feature.lists.presentation.delete_list

import com.buntupana.tmdb.core.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data class DeleteListNav(
    val listId: Long,
    val listName: String
): Route