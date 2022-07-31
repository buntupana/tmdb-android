package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.presentation.composables.widget.menu_selector.MenuSelectorItem
import com.buntupana.tmdb.feature.discover.R

sealed class TrendingFilter(strRes: Int) : MenuSelectorItem(strRes) {
    object Today : TrendingFilter(R.string.text_today)
    object ThisWeek : TrendingFilter(R.string.text_this_week)
}
