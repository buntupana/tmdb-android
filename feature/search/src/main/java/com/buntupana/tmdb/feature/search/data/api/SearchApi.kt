package com.buntupana.tmdb.feature.search.data.api

import com.buntupana.tmdb.core.data.raw.AnyMediaItemRaw
import com.buntupana.tmdb.core.data.raw.MovieItemRaw
import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.core.data.raw.TvShowRaw
import com.buntupana.tmdb.feature.search.data.raw.PersonRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("trending/all/day")
    suspend fun fetchTrending(): Response<ResponseListRaw<AnyMediaItemRaw>>

    @GET("search/multi")
    suspend fun fetchSearchMedia(
        @Query("query") searchKey: String
    ): Response<ResponseListRaw<AnyMediaItemRaw>>

    @GET("search/movie")
    suspend fun fetchSearchMovies(
        @Query("query") searchKey: String,
        @Query("page") page: Int
    ): Response<ResponseListRaw<MovieItemRaw>>

    @GET("search/tv")
    suspend fun fetchSearchTvShows(
        @Query("query") searchKey: String,
        @Query("page") page: Int
    ): Response<ResponseListRaw<TvShowRaw>>

    @GET("search/person")
    suspend fun fetchSearchPersons(
        @Query("query") searchKey: String,
        @Query("page") page: Int
    ): Response<ResponseListRaw<PersonRaw>>
}