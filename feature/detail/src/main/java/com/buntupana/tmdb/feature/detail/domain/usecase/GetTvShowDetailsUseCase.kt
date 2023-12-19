package com.buntupana.tmdb.feature.detail.domain.usecase

import androidx.compose.ui.text.intl.Locale
import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<Long, MediaDetails.TvShow>() {

    override suspend fun getSource(params: Long): Resource<MediaDetails.TvShow> {

        return when (val resource = detailRepository.getTvShowDetails(params)) {
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> {

                val certification = resource.data.certificationList.firstOrNull {
                    it.countryCode == Locale.current.region
                } ?: resource.data.certificationList.firstOrNull {
                    it.countryCode == "US"
                } ?: resource.data.certificationList.firstOrNull()

                val tvShow = MediaDetails.TvShow(
                    id = resource.data.id,
                    title = resource.data.title,
                    posterUrl = resource.data.posterUrl,
                    backdropUrl = resource.data.backdropUrl,
                    trailerUrl = resource.data.trailerUrl,
                    overview = resource.data.overview,
                    tagLine = resource.data.tagLine,
                    releaseDate = resource.data.releaseDate,
                    userScore = resource.data.userScore,
                    runTime = resource.data.runTime,
                    genreList = resource.data.genreList,
                    ageCertification = certification?.rating.orEmpty(),
                    creatorList = resource.data.creatorList,
                    castList = resource.data.credits.castList,
                    crewList = resource.data.credits.crewList,
                    recommendationList = resource.data.recommendationList,
                    seasonList = resource.data.seasonList,
                    lastEpisode = resource.data.lastEpisode,
                    nextEpisode = resource.data.nextEpisode,
                    isInAir = resource.data.isInAir
                )

                Resource.Success(tvShow)
            }
        }
    }
}