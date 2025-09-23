package com.buntupana.tmdb.core.ui.filter_type

import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem

enum class MediaFilter : ExpandableMenuSelectorItem {
    MOVIES {
        override val strRes: Int = R.string.common_movies
    },
    TV_SHOWS {
        override val strRes: Int = R.string.common_tv_shows
    },
}
