package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.presentation.composables.widget.menu_selector.MenuSelectorItem
import com.buntupana.tmdb.feature.discover.R

sealed class PopularFilter(strRes: Int) : MenuSelectorItem(strRes) {
    object Streaming : PopularFilter(R.string.text_streaming)
    object OnTv : PopularFilter(R.string.text_on_tv)
    object ForRent : PopularFilter(R.string.text_for_rent)
    object InTheatres : PopularFilter(R.string.text_in_theatres)
}
