package com.buntupana.tmdb.feature.detail.data.api

import com.buntupana.tmdb.feature.detail.data.raw.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Long
    ): Response<MovieDetailsRaw>

    @GET("tv/{tvShowId}")
    suspend fun getTvShowDetails(
        @Path("tvShowId") tvShowId: Long
    ): Response<TvShowDetailsRaw>

    @GET("movie/{movieId}/release_dates")
    suspend fun getMovieReleaseDates(
        @Path("movieId") movieId: Long
    ): Response<ReleaseDatesRaw>

    @GET("tv/{tvShowId}/content_ratings")
    suspend fun getTvShowRatings(
        @Path("tvShowId") tvShowId: Long
    ): Response<ContentRatingsRaw>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Long
    ): Response<CreditsRaw>

    @GET("tv/{tvShowId}/credits")
    suspend fun getTvShowCredits(
        @Path("tvShowId") movieId: Long
    ): Response<CreditsRaw>
}