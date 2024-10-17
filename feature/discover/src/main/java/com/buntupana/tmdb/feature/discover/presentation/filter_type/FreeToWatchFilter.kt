package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.presentation.composables.widget.menu_selector.ExpandableMenuSelectorItem

sealed class FreeToWatchFilter(strRes: Int) : ExpandableMenuSelectorItem(strRes) {
    object Movies : FreeToWatchFilter(com.buntupana.tmdb.feature.discover.R.string.text_movies)
    object TvShows : FreeToWatchFilter(com.buntupana.tmdb.feature.discover.R.string.text_tv)
}
