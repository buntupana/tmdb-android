package com.buntupana.tmdb.feature.lists.presentation.delete_item_watchlist_favorites

import com.buntupana.tmdb.core.ui.navigation.Route
import com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites.ScreenType
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class DeleteItemWatchlistFavoritesNav(
    val mediaId: Long,
    val mediaName: String,
    val mediaType: MediaType,
    val screenType: ScreenType
): Route