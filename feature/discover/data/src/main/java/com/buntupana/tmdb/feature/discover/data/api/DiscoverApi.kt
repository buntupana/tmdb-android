package com.buntupana.tmdb.feature.discover.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscoverApi {

    @GET("trending/all/{time_window}")
    suspend fun fetchTrending(
        @Path("time_window") timeWindow: String
    ): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.AnyMediaItemRaw>>

    @GET("discover/movie")
    suspend fun fetchPopularMovies(
        @Query("with_watch_monetization_types") monetizationType: String? = null,
        @Query("watch_region") watchRegion: String? = null,
        @Query("region") region: String? = null,
        @Query("with_release_type") releaseType: String? = null,
        @Query("release_date.gte") fromReleaseDate: String? = null,
        @Query("release_date.lte") toReleaseDate: String? = null,
        @Query("sort_by") sortBy: String? = null
    ): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.MovieItemRaw>>

    @GET("discover/tv")
    suspend fun fetchPopularTvShow(
        @Query("with_watch_monetization_types") monetizationType: String? = null,
        @Query("watch_region") watchRegion: String? = null,
        @Query("region") region: String? = null,
        @Query("with_release_type") releaseType: String? = null,
        @Query("release_date.gte") fromReleaseDate: String? = null,
        @Query("release_date.lte") toReleaseDate: String? = null,
        @Query("sort_by") sortBy: String? = null
    ): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.TvShowRaw>>

    @GET("tv/on_the_air")
    suspend fun fetchTvShowsOnAir(): Response<com.panabuntu.tmdb.core.common.raw.ResponseListRaw<com.panabuntu.tmdb.core.common.raw.TvShowRaw>>
}
