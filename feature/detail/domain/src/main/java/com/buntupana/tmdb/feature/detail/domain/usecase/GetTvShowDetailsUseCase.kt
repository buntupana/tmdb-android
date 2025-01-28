package com.buntupana.tmdb.feature.detail.domain.usecase

import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.map
import java.util.Locale
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {

    suspend operator fun invoke(tvShowId: Long): Result<MediaDetails.TvShow, NetworkError> {

        return detailRepository.getTvShowDetails(tvShowId).map { it ->
            val certification = it.certificationList.firstOrNull {
                it.countryCode == Locale.getDefault().country
            } ?: it.certificationList.firstOrNull {
                it.countryCode == "US"
            } ?: it.certificationList.firstOrNull()

            MediaDetails.TvShow(
                id = it.id,
                title = it.title,
                posterUrl = it.posterUrl,
                backdropUrl = it.backdropUrl,
                trailerUrl = it.trailerUrl,
                overview = it.overview,
                tagLine = it.tagLine,
                releaseDate = it.releaseDate,
                voteAverage = it.userScore,
                voteCount = it.voteCount,
                runTime = it.runTime,
                genreList = it.genreList,
                ageCertification = certification?.rating.orEmpty(),
                creatorList = it.creatorList,
                castList = it.credits.castList,
                crewList = it.credits.crewList,
                recommendationList = it.recommendationList,
                seasonList = it.seasonList,
                lastEpisode = it.lastEpisode,
                nextEpisode = it.nextEpisode,
                isInAir = it.isInAir,
                isFavorite = it.isFavorite,
                isWatchlisted = it.isWatchlisted,
                userRating = it.userRating,
                isRateable = it.isRateable
            )
        }
    }
}