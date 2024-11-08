package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class ReleaseDateCountry(
    val iso_3166_1: String,
    @Json(name = "release_dates")
    val releaseDates: List<ReleaseDateRaw>
)