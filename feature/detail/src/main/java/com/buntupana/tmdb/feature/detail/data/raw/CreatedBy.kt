package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class CreatedBy(
    val id: Long,
    @field:Json(name = "credit_id")
    val creditId: String?,
    val gender: Int?,
    val name: String?,
    @field:Json(name = "profile_path")
    val profilePath: String?
)