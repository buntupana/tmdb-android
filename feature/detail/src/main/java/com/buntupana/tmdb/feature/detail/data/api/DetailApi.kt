package com.buntupana.tmdb.feature.detail.data.api

import com.buntupana.tmdb.feature.detail.data.raw.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {

    @GET("movie/{movieId}?append_to_response=release_dates,videos,credits")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Long
    ): Response<MovieDetailsRaw>

    @GET("tv/{tvShowId}?append_to_response=content_ratings,videos,credits")
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

    @GET("person/{personId}?append_to_response=external_ids,combined_credits")
    suspend fun getPersonDetails(
        @Path("personId") personId: Long
    ): Response<PersonDetailsRaw>

    @GET("person/{personId}/combined_credits")
    suspend fun getPersonFilmography(
        @Path("personId") personId: Long
    ): Response<FilmographyRaw>

    @GET("person/{personId}/external_ids")
    suspend fun getPersonExternalLinks(
        @Path("personId") personId: Long
    ): Response<ExternalLinksRaw>
}