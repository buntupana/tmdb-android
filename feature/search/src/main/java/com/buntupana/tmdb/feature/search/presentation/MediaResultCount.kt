package com.buntupana.tmdb.feature.search.presentation

import androidx.annotation.StringRes

data class MediaResultCount(
    @StringRes val titleResId: Int,
    val resultCount: Int
)
