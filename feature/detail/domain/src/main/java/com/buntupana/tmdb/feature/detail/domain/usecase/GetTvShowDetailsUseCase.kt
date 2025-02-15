package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.provider.UrlProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    private val urlProvider: UrlProvider
) {

    suspend operator fun invoke(tvShowId: Long): Flow<Result<MediaDetails.TvShow, NetworkError>> {

        return detailRepository.getTvShowDetails(tvShowId).map { result ->
            when (result) {
                is Result.Error -> result
                is Result.Success -> {

                    val tvShowDetails = result.data

                    val certification = tvShowDetails.certificationList.firstOrNull {
                        it.countryCode == Locale.getDefault().country
                    } ?: tvShowDetails.certificationList.firstOrNull {
                        it.countryCode == "US"
                    } ?: tvShowDetails.certificationList.firstOrNull()

                    Result.Success(
                        MediaDetails.TvShow(
                            id = tvShowDetails.id,
                            title = tvShowDetails.title,
                            posterUrl = tvShowDetails.posterUrl,
                            backdropUrl = tvShowDetails.backdropUrl,
                            trailerUrl = tvShowDetails.trailerUrl,
                            overview = tvShowDetails.overview,
                            tagLine = tvShowDetails.tagLine,
                            releaseDate = tvShowDetails.releaseDate,
                            voteAverage = tvShowDetails.userScore,
                            voteCount = tvShowDetails.voteCount,
                            runTime = tvShowDetails.runTime,
                            genreList = tvShowDetails.genreList,
                            ageCertification = certification?.rating.orEmpty(),
                            creatorList = tvShowDetails.creatorList,
                            castList = tvShowDetails.credits.castList,
                            crewList = tvShowDetails.credits.crewList,
                            recommendationList = tvShowDetails.recommendationList,
                            seasonList = tvShowDetails.seasonList,
                            lastEpisode = tvShowDetails.lastEpisode,
                            nextEpisode = tvShowDetails.nextEpisode,
                            isInAir = tvShowDetails.isInAir,
                            isFavorite = tvShowDetails.isFavorite,
                            isWatchlisted = tvShowDetails.isWatchlisted,
                            userRating = tvShowDetails.userRating,
                            isRateable = tvShowDetails.isRateable,
                            status = tvShowDetails.status,
                            originalLanguage = tvShowDetails.originalLanguage,
                            type = tvShowDetails.type,
                            externalLinkList = tvShowDetails.externalLinkList,
                            shareLink = urlProvider.getMediaShareLink(
                                mediaType = MediaType.TV_SHOW,
                                mediaId = tvShowId
                            )
                        )
                    )
                }
            }
        }
    }
}