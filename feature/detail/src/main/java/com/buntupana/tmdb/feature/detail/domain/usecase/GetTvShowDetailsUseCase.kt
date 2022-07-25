package com.buntupana.tmdb.feature.detail.domain.usecase

import androidx.compose.ui.text.intl.Locale
import com.buntupana.tmdb.app.domain.usecase.UseCaseResource
import com.buntupana.tmdb.core.domain.entity.Resource
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.repository.DetailRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) : UseCaseResource<Long, MediaDetails.TvShow>() {

    override suspend fun getSource(params: Long): Resource<MediaDetails.TvShow> {

        return coroutineScope {
            val tvShowDetailsDef = async { detailRepository.getTvShowDetails(params) }
            val tvShowCreditsDef = async { detailRepository.getTvShowCredits(params) }
            val tvShowCertificationsDef =
                async { detailRepository.getTvShowCertificationList(params) }

            val tvShowDetailsRes = tvShowDetailsDef.await()
            if (tvShowDetailsRes is Resource.Error) {
                return@coroutineScope Resource.Error<MediaDetails.TvShow>(tvShowDetailsRes.message)
            }
            val tvShowCreditsRes = tvShowCreditsDef.await()
            if (tvShowCreditsRes is Resource.Error) {
                return@coroutineScope Resource.Error<MediaDetails.TvShow>(tvShowCreditsRes.message)
            }
            val tvShowCertificationsRes = tvShowCertificationsDef.await()
            if (tvShowCertificationsRes is Resource.Error) {
                return@coroutineScope Resource.Error<MediaDetails.TvShow>(tvShowCertificationsRes.message)
            }

            if (tvShowDetailsRes is Resource.Success && tvShowCreditsRes is Resource.Success && tvShowCertificationsRes is Resource.Success) {

                var certification = tvShowCertificationsRes.data.firstOrNull { it.countryCode == Locale.current.region }

                if(certification == null) {
                    certification = tvShowCertificationsRes.data.firstOrNull { it.countryCode == "US" }
                }

                val tvShow = MediaDetails.TvShow(
                    tvShowDetailsRes.data.id,
                    tvShowDetailsRes.data.title,
                    tvShowDetailsRes.data.posterUrl,
                    tvShowDetailsRes.data.backdropUrl,
                    tvShowDetailsRes.data.overview,
                    tvShowDetailsRes.data.tagLine,
                    tvShowDetailsRes.data.releaseDate,
                    tvShowDetailsRes.data.userScore,
                    tvShowDetailsRes.data.runTime,
                    tvShowDetailsRes.data.genreList,
                    certification?.rating.orEmpty(),
                    tvShowDetailsRes.data.creatorList,
                    tvShowCreditsRes.data.castList,
                    tvShowCreditsRes.data.crewList
                )

                Resource.Success(tvShow)
            } else {
                Resource.Error<MediaDetails.TvShow>("")
            }
        }
    }
}