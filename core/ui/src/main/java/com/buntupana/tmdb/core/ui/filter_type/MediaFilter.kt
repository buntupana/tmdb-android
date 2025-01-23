package com.buntupana.tmdb.core.ui.filter_type

import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem

sealed class MediaFilter(strRes: Int) : ExpandableMenuSelectorItem(strRes) {
    data object Movies : MediaFilter(R.string.text_movies)
    data object TvShows : MediaFilter(R.string.text_tv_shows)
}
