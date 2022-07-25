package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class ProductionCountry(
    @field:Json(name = "iso_3166_1")
    val iso_3166_1: String,
    val name: String
)