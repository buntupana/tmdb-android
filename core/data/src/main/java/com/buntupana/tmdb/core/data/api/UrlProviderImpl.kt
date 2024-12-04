package com.buntupana.tmdb.core.data.api

import com.panabuntu.tmdb.core.common.BuildConfig
import com.panabuntu.tmdb.core.common.UrlProvider

class UrlProviderImpl : UrlProvider {

    override val API_KEY = BuildConfig.tmdb_api_key
    override val BASE_URL_API = "https://api.themoviedb.org/3/"

    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p"
    override val BASE_URL_POSTER = "$BASE_URL_IMAGE/w342/"
    override val BASE_URL_BACKDROP = "$BASE_URL_IMAGE/w1000_and_h450_multi_faces/"
    override val BASE_URL_PROFILE = "$BASE_URL_IMAGE/w240_and_h266_face/"
    override val BASE_URL_AVATAR = "$BASE_URL_IMAGE/w150_and_h150_face/"

    private val BASE_DEEP_LINK_URL = "app://buntupana.tmdb"
    override val SIGN_IN_DEEP_LINK_URL = "$BASE_DEEP_LINK_URL/signin"

    override val BASE_URL_FACEBOOK = "https://www.facebook.com/"
    override val BASE_URL_INSTAGRAM = "https://instagram.com/"
    override val BASE_URL_X = "https://x.com/"
    override val BASE_URL_IMDB = "https://www.imdb.com/name/"

    override fun getSignInUrl(requestToken: String): String {
        return "https://www.themoviedb.org/authenticate/$requestToken?redirect_to=$SIGN_IN_DEEP_LINK_URL"
    }
}