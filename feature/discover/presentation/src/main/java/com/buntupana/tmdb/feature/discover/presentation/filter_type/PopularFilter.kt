package com.buntupana.tmdb.feature.discover.presentation.filter_type

import com.buntupana.tmdb.core.ui.composables.widget.menu_selector.ExpandableMenuSelectorItem
import com.buntupana.tmdb.feature.discover.presentation.R

enum class PopularFilter : ExpandableMenuSelectorItem {
    STREAMING{
        override val strRes: Int = R.string.discover_streaming
    },
    ON_TV{
        override val strRes: Int = R.string.discover_on_tv
    },
    FOR_RENT{
        override val strRes: Int = R.string.discover_for_rent
    },
    IN_THEATRES{
        override val strRes: Int = R.string.discover_in_theatres
    }
}
