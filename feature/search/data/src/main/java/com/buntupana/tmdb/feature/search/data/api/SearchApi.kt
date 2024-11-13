package com.buntupana.tmdb.feature.search.data.api

import com.buntupana.tmdb.feature.search.data.raw.PersonRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("trending/all/day")
    suspend fun fetchTrending(): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.AnyMediaItemRaw>>

    @GET("search/multi")
    suspend fun fetchSearchMedia(
        @Query("query") searchKey: String
    ): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.AnyMediaItemRaw>>

    @GET("search/movie")
    suspend fun fetchSearchMovies(
        @Query("query") searchKey: String,
        @Query("page") page: Int
    ): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.MovieItemRaw>>

    @GET("search/tv")
    suspend fun fetchSearchTvShows(
        @Query("query") searchKey: String,
        @Query("page") page: Int
    ): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.TvShowRaw>>

    @GET("search/person")
    suspend fun fetchSearchPersons(
        @Query("query") searchKey: String,
        @Query("page") page: Int
    ): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<PersonRaw>>
}