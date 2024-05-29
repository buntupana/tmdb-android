package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class SpokenLanguage(
    val name: String,
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "iso_639_1")
    val iso_639_1: String
)