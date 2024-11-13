package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.Resource
import com.panabuntu.tmdb.core.common.usecase.UseCaseResource
import java.time.LocalDate
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<Long, PersonFullDetails>() {

    companion object {
        private const val KNOWN_FOR_SIZE = 8
    }

    override suspend fun getSource(params: Long): Resource<PersonFullDetails> {

        return when (val resource = detailRepository.getPersonDetails(params)) {
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {

                val externalLinks = mutableListOf<ExternalLink>()

                externalLinks.addAll(resource.data.externalLinkList)

//                if (personDetails.imdbLink.isNotBlank()) {
//                    externalLinks.add(ExternalLink.ImdbLink(personDetails.imdbLink))
//                }

                if (resource.data.homePageUrl.isNotBlank()) {
                    externalLinks.add(ExternalLink.HomePage(resource.data.homePageUrl))
                }

                val knownForList = resource.data.filmography
                    .distinctBy { it.title }
                    .filter { it.department == resource.data.knownForDepartment }
                    .sortedByDescending {

                        val firstSortingValue = it.voteCount * it.userScore

                        when (it) {
                            is CreditPersonItem.Movie -> firstSortingValue / (it.castOrder + 1f)
                            is CreditPersonItem.TvShow -> {
                                when {
                                    it.episodeCount < 8 -> 0f
                                    it.episodeCount <= 100 -> firstSortingValue
                                    else -> firstSortingValue * (it.episodeCount.toFloat() / 100f)
                                }
                            }
                        }.toInt()
                    }.take(KNOWN_FOR_SIZE)

                val creditMap =
                    resource.data.filmography
                        .sortedByDescending { it.releaseDate ?: LocalDate.now().plusYears(1) }
                        .groupBy { it.department }

                val personFullDetails = PersonFullDetails(
                    resource.data.id,
                    resource.data.name,
                    resource.data.profileUrl,
                    resource.data.knownForDepartment,
                    resource.data.gender,
                    resource.data.birthDate,
                    resource.data.deathDate,
                    resource.data.age,
                    resource.data.placeOfBirth,
                    resource.data.biography,
                    externalLinks,
                    knownForList,
                    creditMap,
                    resource.data.filmography.size
                )

                Resource.Success(personFullDetails)
            }
        }
    }
}