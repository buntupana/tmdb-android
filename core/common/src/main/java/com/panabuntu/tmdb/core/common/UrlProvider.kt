package com.panabuntu.tmdb.core.common

interface UrlProvider {

    val API_KEY: String
    val BASE_URL_API: String

    val BASE_URL_POSTER: String
    val BASE_URL_BACKDROP: String
    val BASE_URL_PROFILE: String
    val BASE_URL_AVATAR: String

    val SIGN_IN_DEEP_LINK_URL: String

    fun getSignInUrl(requestToken: String): String
    val BASE_URL_FACEBOOK: String
    val BASE_URL_INSTAGRAM: String
    val BASE_URL_X: String
    val BASE_URL_IMDB: String
}