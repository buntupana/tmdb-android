package com.buntupana.tmdb.feature.detail.domain.usecase

import androidx.compose.ui.text.intl.Locale
import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.CrewItem
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<Long, MediaDetails.Movie>() {

    override suspend fun getSource(params: Long): Resource<MediaDetails.Movie> {

        return coroutineScope {
            val movieDetailsDef = async { detailRepository.getMovieDetails(params) }
            val movieReleaseListDef = async { detailRepository.getMovieReleaseDates(params) }
            val movieCreditsDef = async { detailRepository.getMovieCredits(params) }

            val movieDetails = movieDetailsDef.await()
            if (movieDetails is Resource.Error) {
                return@coroutineScope Resource.Error<MediaDetails.Movie>(movieDetails.message)
            }
            val movieReleaseList = movieReleaseListDef.await()
            if (movieReleaseList is Resource.Error) {
                return@coroutineScope Resource.Error<MediaDetails.Movie>(movieReleaseList.message)
            }
            val movieCredits = movieCreditsDef.await()
            if (movieCredits is Resource.Error) {
                return@coroutineScope Resource.Error<MediaDetails.Movie>(movieCredits.message)
            }

            if (movieDetails is Resource.Success && movieReleaseList is Resource.Success && movieCredits is Resource.Success) {

                // Checking if there is a release and certification for default locale
                var releaseAndCertification =
                    movieReleaseList.data.firstOrNull { it.countryCode == Locale.current.region }

                // if not we can try to get the release and certification from the production country
                if (releaseAndCertification == null) {
                    releaseAndCertification =
                        movieReleaseList.data.firstOrNull {
                            it.countryCode == movieDetails.data.productionCountryCodeList.firstOrNull()
                        }
                }

                var certification = releaseAndCertification?.certification.orEmpty()

                if (certification.isBlank()) {
                    certification =
                        movieReleaseList.data.firstOrNull { it.countryCode == "US" }?.certification.orEmpty()
                }

                val localReleaseDate = releaseAndCertification?.releaseDate
                val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
                    java.util.Locale.getDefault()
                )

                val creatorJobList =
                    listOf("Director", "Writer", "Characters", "Screenplay", "Story")

                val creatorList =
                    movieCredits.data.crewList.filter { creatorJobList.contains(it.job) }
                        .groupBy { it.id }.map {
                        CrewItem(
                            it.key,
                            it.value.firstOrNull()?.name.orEmpty(),
                            it.value.firstOrNull()?.profileUrl.orEmpty(),
                            it.value.joinToString(", ") { crewItem -> crewItem.job }
                        )
                    }

                Resource.Success(
                    MediaDetails.Movie(
                        movieDetails.data.id,
                        movieDetails.data.title,
                        movieDetails.data.posterUrl,
                        movieDetails.data.backdropUrl,
                        movieDetails.data.overview,
                        movieDetails.data.tagLine,
                        movieDetails.data.releaseDate,
                        localReleaseDate?.format(dateFormatter),
                        movieDetails.data.userScore,
                        movieDetails.data.runTime,
                        movieDetails.data.genreList,
                        certification,
                        creatorList,
                        movieCredits.data.castList,
                        movieCredits.data.crewList,
                        releaseAndCertification?.countryCode.orEmpty()
                    )
                )
            } else {
                Resource.Error("")
            }
        }
    }
}