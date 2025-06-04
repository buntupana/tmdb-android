package com.panabuntu.tmdb.core.common.provider

import com.panabuntu.tmdb.core.common.entity.MediaType

interface UrlProvider {

    val API_KEY: String
    val BASE_URL_API: String

    val BASE_URL_POSTER: String
    val BASE_URL_BACKDROP: String
    val BASE_URL_PROFILE: String
    val BASE_URL_AVATAR: String
    val BASE_URL_PROVIDER: String

    val SIGN_IN_DEEP_LINK_URL: String

    fun getSignInUrl(requestToken: String): String
    val BASE_URL_FACEBOOK: String
    val BASE_URL_INSTAGRAM: String
    val BASE_URL_X: String
    val BASE_URL_IMDB_PERSON: String
    val BASE_URL_TIKTOK: String
    val BASE_URL_IMDB_MEDIA: String
    val SIGN_IN_DEEP_LINK_REDIRECT: String
    fun getMediaShareLink(mediaType: MediaType, mediaId: Long): String
    fun getPersonShareLink(personId: Long): String
    fun getListShareLink(listId: Long): String
}