package com.buntupana.tmdb.feature.discover.presentation.media_list.filters

import com.buntupana.tmdb.feature.discover.presentation.R

enum class TvShowDefaultFilter(
    val filterNameResId: Int
) {
    POPULAR(R.string.text_popular),
    AIRING_TODAY(R.string.text_airing_today),
    ON_TV(R.string.text_on_tv),
    TOP_RATED(R.string.text_top_rated),
    CUSTOM(R.string.text_custom)
}