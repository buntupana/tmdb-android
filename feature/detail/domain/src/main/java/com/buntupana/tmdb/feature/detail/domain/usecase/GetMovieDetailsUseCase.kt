package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(movieId: Long): Result<MediaDetails.Movie, NetworkError> {

        detailRepository.getMovieDetails(movieId)
            .onError {
                return Result.Error(it)
            }
            .onSuccess { movieDetails ->

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
                val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
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

                return Result.Success(
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
                        runTime = movieDetails.runTime,
                        genreList = movieDetails.genreList,
                        ageCertification = certification,
                        creatorList = creatorList,
                        castList = movieDetails.credits.castList,
                        crewList = movieDetails.credits.crewList,
                        recommendationList = movieDetails.recommendationList,
                        localCountryCodeRelease = releaseAndCertification?.countryCode.orEmpty(),
                        isFavorite = movieDetails.isFavorite,
                        isWatchlisted = movieDetails.isWatchlisted,
                        userRating = movieDetails.userRating,
                        isRateable = movieDetails.isRateable
                    )
                )
            }
        return Result.Error(NetworkError.UNKNOWN)
    }
}