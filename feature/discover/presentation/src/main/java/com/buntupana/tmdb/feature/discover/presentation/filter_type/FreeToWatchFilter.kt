package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem
import com.buntupana.tmdb.feature.discover.presentation.R

sealed class FreeToWatchFilter(strRes: Int) : ExpandableMenuSelectorItem(strRes) {
    object Movies : FreeToWatchFilter(R.string.text_movies)
    object TvShows : FreeToWatchFilter(R.string.text_tv)
}
