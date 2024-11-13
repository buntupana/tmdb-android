package com.panabuntu.tmdb.core.common.raw

import com.squareup.moshi.Json

data class ResponseListRaw<ITEM>(
    val page: Int,
    val results: List<ITEM>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)
