package com.buntupana.tmdb.feature.detail.data.raw

import com.squareup.moshi.Json

data class ExternalLinksRaw(
    @Json(name = "facebook_id")
    val facebookId: String?,
    @Json(name = "freebase_id")
    val freebaseId: String?,
    @Json(name = "freebase_mid")
    val freebaseMid: String?,
    @Json(name = "imdb_id")
    val imdbId: String?,
    @Json(name = "instagram_id")
    val instagramId: String?,
    @Json(name = "tvrage_id")
    val tvrageId: Int?,
    @Json(name = "twitter_id")
    val twitterId: String?
)