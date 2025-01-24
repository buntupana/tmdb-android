package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem
import com.buntupana.tmdb.feature.discover.presentation.R

enum class TrendingFilter : ExpandableMenuSelectorItem {
    TODAY {
        override val strRes: Int = R.string.text_today
    },
    THIS_WEEK {
        override val strRes: Int = R.string.text_this_week
    }
}
