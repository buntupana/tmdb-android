package com.buntupana.tmdb.feature.detail.data.raw

data class RecommendationsRaw(
    val page: Int,
    val results: List<com.panabuntu.tmdb.core.common.raw.AnyMediaItemRaw>
)
