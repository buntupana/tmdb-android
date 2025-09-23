package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.annotation.StringRes
import com.buntupana.tmdb.feature.discover.presentation.R

enum class SortBySimple(
    @StringRes val stringResId: Int,
) {
    POPULARITY(R.string.discover_sort_by_popularity),
    RATING(R.string.discover_sort_by_rating),
    RELEASE_DATE(R.string.discover_sort_by_release_date),
    TITLE(R.string.discover_sort_by_title),
}

enum class SortByOrder(
    @StringRes val stringResId: Int
) {
    ASCENDING(R.string.discover_ascending),
    DESCENDING(R.string.discover_descending)
}