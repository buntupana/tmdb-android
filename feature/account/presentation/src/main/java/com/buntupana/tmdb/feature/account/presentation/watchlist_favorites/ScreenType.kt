package com.buntupana.tmdb.feature.account.presentation.watchlist_favorites

import androidx.annotation.StringRes
import com.buntupana.tmdb.core.ui.R

enum class ScreenType(
    @StringRes val titleResId: Int,
) {
    WATCHLIST(R.string.text_watchlist),
    FAVORITES(R.string.text_favorites)
}