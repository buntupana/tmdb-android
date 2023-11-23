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
                            Person.Crew(
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
                        resource.data.id,
                        resource.data.title,
                        resource.data.posterUrl,
                        resource.data.backdropUrl,
                        resource.data.trailerUrl,
                        resource.data.overview,
                        resource.data.tagLine,
                        resource.data.releaseDate,
                        localReleaseDate?.format(dateFormatter),
                        resource.data.userScore,
                        resource.data.runTime,
                        resource.data.genreList,
                        certification,
                        creatorList,
                        resource.data.credits.castList,
                        resource.data.credits.crewList,
                        releaseAndCertification?.countryCode.orEmpty()
                    )
                )
            }
        }
    }
}