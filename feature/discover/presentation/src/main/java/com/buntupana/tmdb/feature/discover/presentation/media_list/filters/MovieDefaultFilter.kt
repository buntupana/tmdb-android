package com.buntupana.tmdb.feature.discover.presentation.media_list.filters

import com.buntupana.tmdb.feature.discover.presentation.R

enum class MovieDefaultFilter(
    val filterNameResId: Int
) {
    POPULAR(R.string.text_popular),
    NOW_PLAYING(R.string.text_now_playing),
    UPCOMING(R.string.text_upcoming),
    TOP_RATED(R.string.text_top_rated),
    CUSTOM(R.string.text_custom)
}