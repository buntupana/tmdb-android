package com.panabuntu.tmdb.core.common.api

interface CoreApi {

    companion object {
        const val BASE_URL_API = "https://api.themoviedb.org/3/"

        private const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p"
        const val BASE_URL_POSTER = "$BASE_URL_IMAGE/w342/"
        const val BASE_URL_BACKDROP = "$BASE_URL_IMAGE/w1000_and_h450_multi_faces/"
        const val BASE_URL_PROFILE = "$BASE_URL_IMAGE/w240_and_h266_face/"
        const val BASE_URL_AVATAR = "$BASE_URL_IMAGE/w200_and_h200_face/"

        private const val BASE_DEEP_LINK_URL = "app://buntupana.tmdb"
        const val SIGN_IN_DEEP_LINK_URL = "$BASE_DEEP_LINK_URL/signin"

        fun getSignInUrl(requestToken: String): String {
            return "https://www.themoviedb.org/authenticate/$requestToken?redirect_to=$SIGN_IN_DEEP_LINK_URL"
        }
    }
}