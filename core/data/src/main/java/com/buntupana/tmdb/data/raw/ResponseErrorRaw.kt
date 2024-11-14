package com.buntupana.tmdb.data.raw

import com.squareup.moshi.Json

data class ResponseErrorRaw(
    @Json(name = "status_message")
    val statusMessage: String,
    @Json(name = "status_code")
    val statusCode: Int
)
