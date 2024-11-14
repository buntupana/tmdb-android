package com.buntupana.tmdb.feature.detail.data.raw

import com.buntupana.tmdb.data.raw.AnyMediaItemRaw

data class RecommendationsRaw(
    val page: Int,
    val results: List<AnyMediaItemRaw>
)
