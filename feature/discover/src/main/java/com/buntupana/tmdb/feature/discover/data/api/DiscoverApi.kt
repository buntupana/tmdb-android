package com.buntupana.tmdb.feature.discover.data.api

import com.buntupana.tmdb.core.data.raw.ResponseListRaw
import com.buntupana.tmdb.feature.discover.data.raw.MovieItemRaw
import com.buntupana.tmdb.feature.discover.data.raw.TvShowRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscoverApi {

    @GET("trending/movie/{time_window}")
    suspend fun fetchTrendingMovie(
        @Path("time_window") timeWindow: String
    ): Response<ResponseListRaw<MovieItemRaw>>

    @GET("trending/tv/{time_window}")
    suspend fun fetchTrendingTV(
        @Path("time_window") timeWindow: String
    ): Response<ResponseListRaw<TvShowRaw>>

    @GET("discover/movie")
    suspend fun fetchPopularMovies(
        @Query("with_watch_monetization_types") monetizationType: String? = null,
        @Query("watch_region") watchRegion: String? = null,
        @Query("with_release_type") releaseType: String? = null
    ): Response<ResponseListRaw<MovieItemRaw>>
}
