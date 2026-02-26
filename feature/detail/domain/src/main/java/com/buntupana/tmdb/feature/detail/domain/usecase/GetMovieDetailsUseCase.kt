package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

class GetMovieDetailsUseCase(
    private val detailRepository: DetailRepository,
    private val urlProvider: UrlProvider,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(movieId: Long): Flow<Result<MediaDetails.Movie, NetworkError>> {

        val session = sessionManager.session.value

        return detailRepository.getMovieDetails(movieId).map { result ->
            when (result) {
                is Result.Error -> result
                is Result.Success -> {
                    val movieDetails = result.data
                    // Checking if there is a release and certification for session country code
                    // if not we can try to get the release and certification from the origin country
                    val localReleaseDateInfo = movieDetails.releaseDateInfoList.firstOrNull {
                        it.countryCode == session.countryCode
                    } ?: movieDetails.releaseDateInfoList.firstOrNull {
                        it.countryCode == movieDetails.originCountryList.firstOrNull()
                    } ?: movieDetails.releaseDateInfoList.firstOrNull()

                    val certification =
                        (movieDetails.releaseDateInfoList.firstOrNull {
                            it.countryCode == session.countryCode && it.certification.isNotNullOrBlank()
                        } ?: movieDetails.releaseDateInfoList.firstOrNull {
                            it.countryCode == movieDetails.originCountryList.firstOrNull() && it.certification.isNotNullOrBlank()
                        } ?: movieDetails.releaseDateInfoList.firstOrNull())?.certification

                    val dateFormatter =
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
                            Locale.getDefault()
                        )

                    val creatorJobList =
                        listOf("Director", "Writer", "Characters", "Screenplay", "Story", "Novel")

                    val creatorList =
                        movieDetails.creditsMovie.crewList.filter { creatorJobList.contains(it.job) }
                            .groupBy { it.id }.map {
                                Person.Crew.Movie(
                                    id = it.key,
                                    name = it.value.firstOrNull()?.name.orEmpty(),
                                    gender = it.value.firstOrNull()?.gender
                                        ?: com.panabuntu.tmdb.core.common.model.Gender.NOT_SPECIFIED,
                                    profileUrl = it.value.firstOrNull()?.profileUrl.orEmpty(),
                                    department = "",
                                    job = it.value.joinToString(", ") { crewItem -> crewItem.job }
                                )
                            }

                    Result.Success(
                        MediaDetails.Movie(
                            id = movieDetails.id,
                            title = movieDetails.title,
                            originalTitle = movieDetails.originalTitle,
                            posterUrl = movieDetails.posterUrl,
                            backdropUrl = movieDetails.backdropUrl,
                            trailerUrl = movieDetails.trailerUrl,
                            overview = movieDetails.overview,
                            tagLine = movieDetails.tagLine,
                            releaseDate = movieDetails.releaseDate,
                            localReleaseDate = localReleaseDateInfo?.releaseDate?.format(
                                dateFormatter
                            ),
                            voteAverage = movieDetails.userScore,
                            voteCount = movieDetails.voteCount,
                            runTime = movieDetails.runTime,
                            genreList = movieDetails.genreList,
                            ageCertification = certification.orEmpty(),
                            creatorList = creatorList,
                            castList = movieDetails.creditsMovie.castList,
                            crewList = movieDetails.creditsMovie.crewList,
                            recommendationList = movieDetails.recommendationList,
                            localCountryCodeRelease = localReleaseDateInfo?.countryCode.orEmpty(),
                            isFavorite = movieDetails.isFavorite,
                            isWatchlisted = movieDetails.isWatchListed,
                            userRating = movieDetails.userRating,
                            isRateable = movieDetails.isRateable,
                            status = movieDetails.status,
                            originalLanguage = movieDetails.originalLanguage,
                            budget = movieDetails.budget,
                            revenue = movieDetails.revenue,
                            externalLinkList = movieDetails.externalLinkList,
                            shareLink = urlProvider.getMediaShareLink(
                                mediaType = MediaType.MOVIE,
                                mediaId = movieId
                            ),
                            providers = movieDetails.providers
                        )
                    )
                }
            }
        }
    }
}