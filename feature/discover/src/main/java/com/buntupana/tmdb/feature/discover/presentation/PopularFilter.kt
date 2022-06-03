package com.buntupana.tmdb.feature.discover.presentation

import com.buntupana.tmdb.core.presentation.widget.menu_selector.MenuSelectorItem

sealed class PopularFilter(strRes: Int) : MenuSelectorItem(strRes) {
    object Streaming : PopularFilter(com.buntupana.tmdb.feature.discover.R.string.text_streaming)
    object OnTv : PopularFilter(com.buntupana.tmdb.feature.discover.R.string.text_on_tv)
    object ForRent : PopularFilter(com.buntupana.tmdb.feature.discover.R.string.text_for_rent)
    object InTheatres : PopularFilter(com.buntupana.tmdb.feature.discover.R.string.text_in_theatres)
}
