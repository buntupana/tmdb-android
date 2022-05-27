package com.buntupana.tmdb.core.data.raw

import com.squareup.moshi.Json

data class ResponseErrorRaw(
    @field:Json(name = "status_message")
    val statusMessage: String,
    @field:Json(name = "status_code")
    val statusCode: Int
)
