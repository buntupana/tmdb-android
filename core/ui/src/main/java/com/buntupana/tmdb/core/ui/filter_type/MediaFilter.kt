package com.buntupana.tmdb.core.ui.filter_type

import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem

enum class MediaFilter : ExpandableMenuSelectorItem {
    MOVIE {
        override val strRes: Int = R.string.text_movies
    },
    TV_SHOW {
        override val strRes: Int = R.string.text_tv_shows
    },
}
