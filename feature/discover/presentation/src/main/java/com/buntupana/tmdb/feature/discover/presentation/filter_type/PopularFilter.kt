package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem
import com.buntupana.tmdb.feature.discover.presentation.R

sealed class PopularFilter(strRes: Int) : ExpandableMenuSelectorItem(strRes) {
    object Streaming : PopularFilter(R.string.text_streaming)
    object OnTv : PopularFilter(R.string.text_on_tv)
    object ForRent : PopularFilter(R.string.text_for_rent)
    object InTheatres : PopularFilter(R.string.text_in_theatres)
}
