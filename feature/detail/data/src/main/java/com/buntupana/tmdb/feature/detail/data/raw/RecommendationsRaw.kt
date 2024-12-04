package com.buntupana.tmdb.feature.detail.data.raw

import com.buntupana.tmdb.core.data.raw.AnyMediaItemRaw
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsRaw(
    val page: Int,
    val results: List<AnyMediaItemRaw>
)
