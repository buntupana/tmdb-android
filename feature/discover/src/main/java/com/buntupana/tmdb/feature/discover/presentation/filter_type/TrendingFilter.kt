package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.presentation.composables.widget.menu_selector.ExpandableMenuSelectorItem
import com.buntupana.tmdb.feature.discover.R

sealed class TrendingFilter(strRes: Int) : ExpandableMenuSelectorItem(strRes) {
    object Today : TrendingFilter(R.string.text_today)
    object ThisWeek : TrendingFilter(R.string.text_this_week)
}
