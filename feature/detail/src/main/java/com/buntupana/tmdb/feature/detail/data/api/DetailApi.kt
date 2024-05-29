package com.buntupana.tmdb.feature.detail.data.api

import com.buntupana.tmdb.feature.detail.data.raw.ContentRatingsRaw
import com.buntupana.tmdb.feature.detail.data.raw.CreditsMovieRaw
import com.buntupana.tmdb.feature.detail.data.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.data.raw.FilmographyRaw
import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.ReleaseDatesRaw
import com.buntupana.tmdb.feature.detail.data.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailApi {

    @GET("movie/{movieId}?append_to_response=release_dates,videos,credits,recommendations")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Long
    ): Response<MovieDetailsRaw>

    @GET("tv/{tvShowId}?append_to_response=content_ratings,videos,aggregate_credits,recommendations")
    suspend fun getTvShowDetails(
        @Path("tvShowId") tvShowId: Long
    ): Response<TvShowDetailsRaw>

    @GET("tv/{series_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("series_id") tvShowId: Long,
        @Path("season_number") seasonNumber: Int
    ): Response<SeasonDetailsRaw>

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
    ): Response<CreditsMovieRaw>

    @GET("tv/{tvShowId}/credits")
    suspend fun getTvShowCredits(
        @Path("tvShowId") movieId: Long
    ): Response<CreditsMovieRaw>

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