package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class ExternalLinksRaw(
    val id: Long,
    @field:Json(name = "facebook_id")
    val facebookId: String?,
    @field:Json(name = "freebase_id")
    val freebaseId: String?,
    @field:Json(name = "freebase_mid")
    val freebaseMid: String?,
    @field:Json(name = "imdb_id")
    val imdbId: String?,
    @field:Json(name = "instagram_id")
    val instagramId: String?,
    @field:Json(name = "tvrage_id")
    val tvrageId: Int?,
    @field:Json(name = "twitter_id")
    val twitterId: String?
)