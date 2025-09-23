package com.buntupana.tmdb.feature.lists.presentation.watchlist_favorites

import androidx.annotation.StringRes
import com.buntupana.tmdb.core.ui.R

enum class ScreenType(
    @StringRes val titleResId: Int,
) {
    WATCHLIST(R.string.common_watchlist),
    FAVORITES(R.string.common_favorites)
}