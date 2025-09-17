package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

import kotlinx.serialization.Serializable

@Serializable
sealed class ListType(
) {
    @Serializable
    data class CustomList(val listId: Long) : ListType()

    @Serializable
    data object Watchlist : ListType()

    @Serializable
    data object Favorites : ListType()
}