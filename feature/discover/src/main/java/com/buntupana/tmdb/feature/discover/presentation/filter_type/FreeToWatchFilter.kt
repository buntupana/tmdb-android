package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.presentation.composables.widget.menu_selector.MenuSelectorItem

sealed class FreeToWatchFilter(strRes: Int) : MenuSelectorItem(strRes) {
    object Movies : FreeToWatchFilter(com.buntupana.tmdb.feature.discover.R.string.text_movies)
    object TvShows : FreeToWatchFilter(com.buntupana.tmdb.feature.discover.R.string.text_tv)
}
