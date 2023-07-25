package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import java.time.LocalDate
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<Long, PersonFullDetails>() {

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

                val knownForList =
                    resource.data.filmography.filter { it.department == resource.data.knownForDepartment }
                        .sortedByDescending { it.voteCount }.distinctBy { it.title }.take(9)

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