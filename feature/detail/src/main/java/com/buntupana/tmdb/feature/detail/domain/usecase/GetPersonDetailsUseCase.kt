package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.ExternalLink
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<Long, PersonFullDetails>() {

    override suspend fun getSource(params: Long): Resource<PersonFullDetails> {

        return coroutineScope {
            val personDetailsDef = async { detailRepository.getPersonDetails(params) }
            val filmographyListDef = async { detailRepository.getPersonFilmography(params) }
            val externalLinksDef = async { detailRepository.getPersonExternalLinks(params) }

            val personDetailsRes = personDetailsDef.await()
            if (personDetailsRes is Resource.Error) {
                return@coroutineScope Resource.Error<PersonFullDetails>(personDetailsRes.message)
            }
            val filmographyListRes = filmographyListDef.await()
            if (filmographyListRes is Resource.Error) {
                return@coroutineScope Resource.Error<PersonFullDetails>(filmographyListRes.message)
            }
            val externalListRes = externalLinksDef.await()
            if (externalListRes is Resource.Error) {
                return@coroutineScope Resource.Error<PersonFullDetails>(externalListRes.message)
            }

            if (personDetailsRes is Resource.Success && filmographyListRes is Resource.Success && externalListRes is Resource.Success) {

                val personDetails = personDetailsRes.data

                val externalLinks = mutableListOf<ExternalLink>()

                externalLinks.addAll(externalListRes.data)

//                if (personDetails.imdbLink.isNotBlank()) {
//                    externalLinks.add(ExternalLink.ImdbLink(personDetails.imdbLink))
//                }

                if (personDetails.homePageUrl.isNotBlank()) {
                    externalLinks.add(ExternalLink.HomePage(personDetails.homePageUrl))
                }

                val personFullDetails = PersonFullDetails(
                    personDetails.id,
                    personDetails.name,
                    personDetails.profileUrl,
                    personDetails.knownForDepartment,
                    personDetails.gender,
                    personDetails.birthDate,
                    personDetails.deathDate,
                    personDetails.age,
                    personDetails.placeOfBirth,
                    personDetails.biography,
                    externalLinks,
                    filmographyListRes.data,
                    filmographyListRes.data.size
                )

                Resource.Success(personFullDetails)
            } else {
                Resource.Error("")
            }
        }
    }
}