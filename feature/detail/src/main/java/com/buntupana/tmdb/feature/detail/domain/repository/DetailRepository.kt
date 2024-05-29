package com.buntupana.tmdb.feature.detail.domain.repository

import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.Certification
import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.buntupana.tmdb.feature.detail.domain.model.Credits
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.domain.model.MovieDetails
import com.buntupana.tmdb.feature.detail.domain.model.PersonDetails
import com.buntupana.tmdb.feature.detail.domain.model.ReleaseDate
import com.buntupana.tmdb.feature.detail.domain.model.SeasonDetail
import com.buntupana.tmdb.feature.detail.domain.model.TvShowDetails

interface DetailRepository {
    suspend fun getMovieDetails(movieId: Long): Resource<MovieDetails>
    suspend fun getTvShowDetails(tvShowId: Long): Resource<TvShowDetails>
    suspend fun getMovieReleaseDates(movieId: Long): Resource<List<ReleaseDate>>
    suspend fun getMovieCredits(movieId: Long): Resource<Credits>
    suspend fun getTvShowCredits(tvShowId: Long): Resource<Credits>
    suspend fun getTvShowCertificationList(tvShowId: Long): Resource<List<Certification>>
    suspend fun getPersonDetails(personId: Long): Resource<PersonDetails>
    suspend fun getPersonFilmography(personId: Long): Resource<List<CreditPersonItem>>
    suspend fun getPersonExternalLinks(personId: Long): Resource<List<ExternalLink>>
    suspend fun getSeasonDetails(tvShowId: Long, episodeNumber: Int): Resource<SeasonDetail>
}