package com.buntupana.tmdb.feature.detail.data.remote_data_source

import com.buntupana.tmdb.feature.detail.data.api.DetailApi
import com.buntupana.tmdb.feature.detail.data.raw.ContentRatingsRaw
import com.buntupana.tmdb.feature.detail.data.raw.CreditsMovieRaw
import com.buntupana.tmdb.feature.detail.data.raw.CreditsTvShowRaw
import com.buntupana.tmdb.feature.detail.data.raw.ExternalLinksRaw
import com.buntupana.tmdb.feature.detail.data.raw.FilmographyRaw
import com.buntupana.tmdb.feature.detail.data.raw.MovieDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.PersonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.ReleaseDatesRaw
import com.buntupana.tmdb.feature.detail.data.raw.SeasonDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.TvShowDetailsRaw
import com.buntupana.tmdb.feature.detail.data.raw.TvShowSeasonsDetailsRaw
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.remote_data_source.RemoteDataSource
import javax.inject.Inject

class DetailRemoteDataSource @Inject constructor(
    private val detailApi: DetailApi
) : RemoteDataSource() {

    suspend fun getMovieDetail(movieId: Long): Resource<MovieDetailsRaw> {
        return getResourceResult { detailApi.getMovieDetails(movieId) }
    }

    suspend fun getTvShowDetail(tvShowId: Long): Resource<TvShowDetailsRaw> {
        return getResourceResult { detailApi.getTvShowDetails(tvShowId) }
    }

    suspend fun getSeasonDetail(tvShowId: Long, seasonNumber: Int): Resource<SeasonDetailsRaw> {
        return getResourceResult { detailApi.getSeasonDetails(tvShowId, seasonNumber) }
    }

    suspend fun getMovieReleaseDateList(movieId: Long): Resource<ReleaseDatesRaw> {
        return getResourceResult { detailApi.getMovieReleaseDates(movieId) }
    }

    suspend fun getTvShowCertificationList(tvShowId: Long): Resource<ContentRatingsRaw> {
        return getResourceResult { detailApi.getTvShowRatings(tvShowId) }
    }

    suspend fun getMovieCredits(movieId: Long): Resource<CreditsMovieRaw> {
        return getResourceResult { detailApi.getMovieCredits(movieId) }
    }

    suspend fun getTvCredits(tvShowId: Long): Resource<CreditsTvShowRaw> {
        return getResourceResult { detailApi.getTvShowCredits(tvShowId) }
    }

    suspend fun getPersonDetails(personId: Long): Resource<PersonDetailsRaw> {
        return getResourceResult { detailApi.getPersonDetails(personId) }
    }

    suspend fun getPersonFilmography(personId: Long): Resource<FilmographyRaw> {
        return getResourceResult { detailApi.getPersonFilmography(personId) }
    }

    suspend fun getPersonExternalLinks(personId: Long): Resource<ExternalLinksRaw> {
        return getResourceResult { detailApi.getPersonExternalLinks(personId) }
    }

    suspend fun getTvShowSeasonsDetails(tvShowId: Long): Resource<TvShowSeasonsDetailsRaw> {
        return getResourceResult { detailApi.getTvShowSeasonsDetails(tvShowId) }
    }
}