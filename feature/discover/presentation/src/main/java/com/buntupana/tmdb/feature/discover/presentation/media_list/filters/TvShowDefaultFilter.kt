package com.buntupana.tmdb.feature.discover.presentation.media_list.filters

import com.buntupana.tmdb.feature.discover.presentation.R

enum class TvShowDefaultFilter(
    val filterNameResId: Int
) {
    POPULAR(R.string.discover_popular),
    AIRING_TODAY(R.string.discover_airing_today),
    ON_TV(R.string.discover_on_tv),
    TOP_RATED(R.string.discover_top_rated),
    CUSTOM(R.string.discover_custom)
}