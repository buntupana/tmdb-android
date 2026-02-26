package com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites

import android.os.Parcelable
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class WatchlistFavoritesResult : Parcelable {
    data class CancelRemoveItem(val mediaId: Long, val mediaType: MediaType) : WatchlistFavoritesResult()
}