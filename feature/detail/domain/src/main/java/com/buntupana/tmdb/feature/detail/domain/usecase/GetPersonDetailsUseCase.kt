package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.CreditPersonItem
import com.buntupana.tmdb.feature.detail.domain.model.PersonFullDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import java.time.LocalDate

class GetPersonDetailsUseCase(
    private val detailRepository: DetailRepository,
    private val urlProvider: UrlProvider
) {

    companion object {
        private const val KNOWN_FOR_SIZE = 8
    }

    suspend operator fun invoke(personId: Long): Result<PersonFullDetails, NetworkError> {

        detailRepository.getPersonDetails(personId)
            .onError { return Result.Error(it) }
            .onSuccess { creditPersonItem ->

                val knownForList = creditPersonItem.filmography
                    .distinctBy { it.title }
                    .filter { it.department == creditPersonItem.knownForDepartment }
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
                    creditPersonItem.filmography
                        .sortedByDescending { it.releaseDate ?: LocalDate.now().plusYears(1) }
                        .groupBy { it.department }

                val personFullDetails = PersonFullDetails(
                    id = creditPersonItem.id,
                    name = creditPersonItem.name,
                    profileUrl = creditPersonItem.profileUrl,
                    knownForDepartment = creditPersonItem.knownForDepartment,
                    gender = creditPersonItem.gender,
                    birthDate = creditPersonItem.birthDate,
                    deathDate = creditPersonItem.deathDate,
                    age = creditPersonItem.age,
                    placeOfBirth = creditPersonItem.placeOfBirth,
                    biography = creditPersonItem.biography,
                    externalLinks = creditPersonItem.externalLinkList,
                    knownFor = knownForList,
                    creditMap = creditMap,
                    knownCredits = creditPersonItem.filmography.size,
                    shareLink = urlProvider.getPersonShareLink(personId)
                )

                return Result.Success(personFullDetails)
            }

        return Result.Error(NetworkError.UNKNOWN)
    }
}