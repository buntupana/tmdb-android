package com.buntupana.tmdb.feature.discover.domain.usecase

import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.PopularType
import com.buntupana.tmdb.feature.discover.domain.repository.DiscoverRepository
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetPopularMoviesListUseCase @Inject constructor(
    private val discoverRepository: DiscoverRepository
) {

    suspend operator fun invoke(popularType: PopularType): Result<List<MediaItem>, NetworkError> {
        return when (popularType) {
            PopularType.STREAMING -> {
                coroutineScope {
                    val movieItemListDef =
                        async { discoverRepository.getMoviesPopular(MonetizationType.FLAT_RATE) }
                    val tvShowItemListDef =
                        async { discoverRepository.getTvShowsPopular(MonetizationType.FLAT_RATE) }

                    val movieItemList = movieItemListDef.await()
                    val tvShowItemList = tvShowItemListDef.await()

                    when {
                        movieItemList is Result.Error -> movieItemList
                        tvShowItemList is Result.Error -> tvShowItemList
                        movieItemList is Result.Success && tvShowItemList is Result.Success -> {
                            val result = movieItemList.data + tvShowItemList.data
                            Result.Success(result.shuffled())
                        }
                        else -> {
                            movieItemList
                        }
                    }
                }
            }
            PopularType.ON_TV -> discoverRepository.getTvShowOnAir()
            PopularType.FOR_RENT -> discoverRepository.getMoviesPopular(MonetizationType.RENT)
            PopularType.IN_THEATRES -> discoverRepository.getMoviesInTheatres()
        }
    }
}