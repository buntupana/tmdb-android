package com.buntupana.tmdb.core.data.api

import com.panabuntu.tmdb.core.common.BuildConfig
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.provider.UrlProvider

class UrlProviderImpl : UrlProvider {

    override val API_KEY = BuildConfig.tmdb_api_key
    override val BASE_URL_API_V3 = "https://api.themoviedb.org/3/"
    override val BASE_URL_API_V4 = "https://api.themoviedb.org/4/"

    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p"
    override val BASE_URL_POSTER = "$BASE_URL_IMAGE/w342/"
    override val BASE_URL_BACKDROP = "$BASE_URL_IMAGE/w1000_and_h450_multi_faces/"
    override val BASE_URL_PROFILE = "$BASE_URL_IMAGE/w240_and_h266_face/"
    override val BASE_URL_AVATAR = "$BASE_URL_IMAGE/w150_and_h150_face/"

    private val BASE_URL_WEB = "https://www.themoviedb.org"

    private val BASE_DEEP_LINK_URL = "app://buntupana.tmdb"
    override val SIGN_IN_DEEP_LINK_URL = "$BASE_DEEP_LINK_URL/signin"
    override val SIGN_IN_DEEP_LINK_REDIRECT = "$SIGN_IN_DEEP_LINK_URL?approved=true"

    override val BASE_URL_FACEBOOK = "https://www.facebook.com/"
    override val BASE_URL_INSTAGRAM = "https://instagram.com/"
    override val BASE_URL_X = "https://x.com/"
    override val BASE_URL_IMDB_PERSON = "https://www.imdb.com/name/"
    override val BASE_URL_IMDB_MEDIA = "https://www.imdb.com/title/"
    override val BASE_URL_TIKTOK = "https://www.tiktok.com/@"

    override fun getSignInUrl(requestToken: String): String {
        return "$BASE_URL_WEB/auth/access?request_token=$requestToken"
    }

    override fun getMediaShareLink(mediaType: MediaType, mediaId: Long): String {
        return "$BASE_URL_WEB/${mediaType.value.lowercase()}/$mediaId"
    }

    override fun getPersonShareLink(personId: Long): String {
        return "$BASE_URL_WEB/person/$personId"
    }
}