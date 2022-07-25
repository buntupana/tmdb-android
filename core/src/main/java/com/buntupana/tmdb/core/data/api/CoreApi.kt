package com.buntupana.tmdb.core.data.api

interface CoreApi {

    companion object {
        const val BASE_URL_API = "https://api.themoviedb.org/3/"
        const val BASE_URL_POSTER = "https://image.tmdb.org/t/p/w342/"
        const val BASE_URL_BACKDROP = "https://image.tmdb.org/t/p/w1000_and_h450_multi_faces"
        const val BASE_URL_PROFILE = "https://image.tmdb.org/t/p/w120_and_h133_face/"
    }
}