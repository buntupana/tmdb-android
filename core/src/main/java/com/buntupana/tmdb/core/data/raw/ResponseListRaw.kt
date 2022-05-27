package com.buntupana.tmdb.core.data.raw

import com.squareup.moshi.Json

data class ResponseListRaw<ITEM>(
    val page: Int,
    val results: List<ITEM>,
    @field:Json(name = "total_pages")
    val totalPages: Int,
    @field:Json(name = "total_results")
    val totalResults: Int
)
