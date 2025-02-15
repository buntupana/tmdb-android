package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    private val urlProvider: UrlProvider
) {
    suspend operator fun invoke(movieId: Long): Flow<Result<MediaDetails.Movie, NetworkError>> {

        return detailRepository.getMovieDetails(movieId).map { result ->
            when (result) {
                is Result.Error -> result
                is Result.Success -> {
                    val movieDetails = result.data
                    // Checking if there is a release and certification for default locale
                    // if not we can try to get the release and certification from the production country
                    val releaseAndCertification =
                        movieDetails.releaseDateList.firstOrNull {
                            it.countryCode == Locale.getDefault().country
                        } ?: movieDetails.releaseDateList.firstOrNull {
                            it.countryCode == movieDetails.productionCountryCodeList.firstOrNull()
                        } ?: movieDetails.releaseDateList.firstOrNull()

                    var certification = releaseAndCertification?.certification.orEmpty()

                    if (certification.isBlank()) {
                        certification =
                            movieDetails.releaseDateList.firstOrNull { it.countryCode == "US" }?.certification.orEmpty()
                    }

                    val localReleaseDate = releaseAndCertification?.releaseDate
                    val dateFormatter =
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
                            Locale.getDefault()
                        )

                    val creatorJobList =
                        listOf("Director", "Writer", "Characters", "Screenplay", "Story", "Novel")

                    val creatorList =
                        movieDetails.credits.crewList.filter { creatorJobList.contains(it.job) }
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
                            posterUrl = movieDetails.posterUrl,
                            backdropUrl = movieDetails.backdropUrl,
                            trailerUrl = movieDetails.trailerUrl,
                            overview = movieDetails.overview,
                            tagLine = movieDetails.tagLine,
                            releaseDate = movieDetails.releaseDate,
                            localReleaseDate = localReleaseDate?.format(dateFormatter),
                            voteAverage = movieDetails.userScore,
                            voteCount = movieDetails.voteCount,
                            runTime = movieDetails.runTime,
                            genreList = movieDetails.genreList,
                            ageCertification = certification,
                            creatorList = creatorList,
                            castList = movieDetails.credits.castList,
                            crewList = movieDetails.credits.crewList,
                            recommendationList = movieDetails.recommendationList,
                            localCountryCodeRelease = releaseAndCertification?.countryCode.orEmpty(),
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
                            )
                        )
                    )
                }
            }
        }
    }
}