package com.buntupana.tmdb.feature.detail.domain.usecase

import androidx.compose.ui.text.intl.Locale
import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.core.domain.model.Gender
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<Long, MediaDetails.Movie>() {

    override suspend fun getSource(params: Long): Resource<MediaDetails.Movie> {

        return when (val resource = detailRepository.getMovieDetails(params)) {
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {

                // Checking if there is a release and certification for default locale
                // if not we can try to get the release and certification from the production country
                val releaseAndCertification =
                    resource.data.releaseDateList.firstOrNull {
                        it.countryCode == Locale.current.region
                    } ?: resource.data.releaseDateList.firstOrNull {
                        it.countryCode == resource.data.productionCountryCodeList.firstOrNull()
                    } ?: resource.data.releaseDateList.firstOrNull()

                var certification = releaseAndCertification?.certification.orEmpty()

                if (certification.isBlank()) {
                    certification =
                        resource.data.releaseDateList.firstOrNull { it.countryCode == "US" }?.certification.orEmpty()
                }

                val localReleaseDate = releaseAndCertification?.releaseDate
                val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(
                    java.util.Locale.getDefault()
                )

                val creatorJobList =
                    listOf("Director", "Writer", "Characters", "Screenplay", "Story", "Novel")

                val creatorList =
                    resource.data.credits.crewList.filter { creatorJobList.contains(it.job) }
                        .groupBy { it.id }.map {
                            Person.Crew.Movie(
                                id = it.key,
                                name = it.value.firstOrNull()?.name.orEmpty(),
                                gender = it.value.firstOrNull()?.gender ?: Gender.NOT_SPECIFIED,
                                profileUrl = it.value.firstOrNull()?.profileUrl.orEmpty(),
                                department = "",
                                job = it.value.joinToString(", ") { crewItem -> crewItem.job }
                            )
                        }

                Resource.Success(
                    MediaDetails.Movie(
                        id = resource.data.id,
                        title = resource.data.title,
                        posterUrl = resource.data.posterUrl,
                        backdropUrl = resource.data.backdropUrl,
                        trailerUrl = resource.data.trailerUrl,
                        overview = resource.data.overview,
                        tagLine = resource.data.tagLine,
                        releaseDate = resource.data.releaseDate,
                        localReleaseDate = localReleaseDate?.format(dateFormatter),
                        userScore = resource.data.userScore,
                        runTime = resource.data.runTime,
                        genreList = resource.data.genreList,
                        ageCertification = certification,
                        creatorList = creatorList,
                        castList = resource.data.credits.castList,
                        crewList = resource.data.credits.crewList,
                        recommendationList = resource.data.recommendationList,
                        localCountryCodeRelease = releaseAndCertification?.countryCode.orEmpty()
                    )
                )
            }
        }
    }
}