package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

import com.buntupana.tmdb.core.ui.navigation.Route
import com.buntupana.tmdb.core.ui.util.navType
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class DeleteItemListRoute(
    val mediaId: Long,
    val mediaName: String,
    val mediaType: MediaType,
    val listType: ListType
): Route {
    companion object Companion {
        val typeMap = mapOf(navType<ListType>())
    }
}