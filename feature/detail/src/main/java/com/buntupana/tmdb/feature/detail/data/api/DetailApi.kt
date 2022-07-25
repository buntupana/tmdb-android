package com.buntupana.tmdb.feature.detail.data.api

import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Long
    ): Response<MovieDetailsRaw>
}