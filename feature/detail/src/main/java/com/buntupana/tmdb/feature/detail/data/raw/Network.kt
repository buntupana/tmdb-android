package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class Network(
    val id: Long,
    @field:Json(name = "logo_path")
    val logoPath: String?,
    val name: String?,
    @field:Json(name = "origin_country")
    val originCountry: String?
)