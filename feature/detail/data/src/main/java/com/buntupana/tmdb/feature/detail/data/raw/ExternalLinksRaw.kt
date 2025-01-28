package com.buntupana.tmdb.feature.detail.data.raw

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalLinksRaw(
    @SerialName("facebook_id")
    val facebookId: String? = null,
    @SerialName("freebase_id")
    val freebaseId: String? = null,
    @SerialName("freebase_mid")
    val freebaseMid: String? = null,
    @SerialName("imdb_id")
    val imdbId: String? = null,
    @SerialName("instagram_id")
    val instagramId: String? = null,
    @SerialName("tvrage_id")
    val tvrageId: Int? = null,
    @SerialName("twitter_id")
    val twitterId: String? = null,
    @SerialName("tiktok_id")
    val tiktokId: String? = null,
    @SerialName("wikidata_id")
    val wikiDataId: String? = null,
    @SerialName("youtube_id")
    val youtubeId: String? = null
)