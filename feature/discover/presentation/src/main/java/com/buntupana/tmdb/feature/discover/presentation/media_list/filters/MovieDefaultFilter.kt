package com.buntupana.tmdb.feature.discover.presentation.media_list.filters

import com.buntupana.tmdb.feature.discover.presentation.R

enum class MovieDefaultFilter(
    val filterNameResId: Int
) {
    POPULAR(R.string.discover_popular),
    NOW_PLAYING(R.string.discover_now_playing),
    UPCOMING(R.string.discover_upcoming),
    TOP_RATED(R.string.discover_top_rated),
    CUSTOM(R.string.discover_custom)
}