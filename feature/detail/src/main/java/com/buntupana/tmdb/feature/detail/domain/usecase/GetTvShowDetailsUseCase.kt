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
                    resource.data.id,
                    resource.data.title,
                    resource.data.posterUrl,
                    resource.data.backdropUrl,
                    resource.data.trailerUrl,
                    resource.data.overview,
                    resource.data.tagLine,
                    resource.data.releaseDate,
                    resource.data.userScore,
                    resource.data.runTime,
                    resource.data.genreList,
                    certification?.rating.orEmpty(),
                    resource.data.creatorList,
                    resource.data.credits.castList,
                    resource.data.credits.crewList
                )

                Resource.Success(tvShow)
            }
        }
    }
}